package com.physicsim.game.model.rigidbody;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;

/**
 * Abstract class for a rigid body physics game object. Must be a convex polygon.
 */
public abstract class RigidBody extends GameObject {
    /** The vertices of this rigid body. */
    private final Vector2[] myVertices;
    /** The edges of this rigid body. */
    private final RigidBodyEdge[] myEdges;
    /** Reusable vector to save some memory. */
    private final Vector2 myCache;
    // physics fields
    /** The uniform mass of the rigid body. */
    private final double myMass;
    // Linear components
    /** The position vector but also the center of mass. */
    private final Vector2 myPosition;
    /** The old position to use verlet integration. */
    private final Vector2 myOldPosition;
    /** The Net acceleration vector. */
    private final Vector2 myAcceleration;
    /** The Net impulse vector. */
    private final Vector2 myImpulse;
    // Rotational components
    // In 2D space, angular components are scalars not vectors
    /** The moment of inertia at the center of mass. */
    private final double myMoi;
    /** The angular position (orientation), measured in radians. */
    private double myAngularPos;
    /** The old angular position. */
    private double myOldAngularPos;
    /** The net angular acceleration. */
    private double myAngularAccel;
    /** The net angular impulse. */
    private double myAngularImpulse;
    /** Whether physics applies to this body. Distinguishes static bodies.*/
    protected boolean hasPhysics;

    /**
     * Creates a rigid body given a bunch of vertices without specifying the center of mass.
     * The center of mass is calculated using the vertices.
     * @param theMass     the center of mass
     * @param theVertices the number of new vertices as vectors
     */
    public RigidBody(final double theMass, final Vector2... theVertices) {
        this(VMath.findCentroid(theVertices), theMass, theVertices);
    }

    /**
     * Creates a rigid body given a bunch of vertices.
     * @param theCenterOfMass the center of mass
     * @param theMass         the uniformly distributed mass
     * @param theVertices     the number of new vertices as vectors
     * @throws IllegalArgumentException if it is not a convex 2d polygon that have 3 or more vertices
     */
    public RigidBody(final Vector2 theCenterOfMass, final double theMass, final Vector2... theVertices) {
        // valid vertices
        if (theVertices.length < 3) throw new IllegalArgumentException("A rigid body must have 3 or more vertices");
        myVertices = theVertices;
        myEdges = new RigidBodyEdge[theVertices.length];

        for (int i = 0; i < myEdges.length; i++) {
            myEdges[i] = new RigidBodyEdge(myVertices[i], myVertices[(i + 1) % myEdges.length]);
        }
        if (!VMath.isConvex(myEdges)) throw new IllegalArgumentException("A rigid body must be convex");

        myCache = new Vector2();
        myMass = theMass;
        myMoi = VMath.findMomentOfInertia(myVertices, theCenterOfMass, myMass);

        myPosition = new Vector2(theCenterOfMass);
        myOldPosition = new Vector2(myPosition);
        myAcceleration = new Vector2();
        myImpulse = new Vector2();
    }

    /**
     * Enables/Disables physics of this body. When disabled, it will be treated as a static object that can still be
     * interacted with.
     * @param thePhysics sets the physics
     */
    public void setPhysics(final boolean thePhysics) {
        hasPhysics = thePhysics;
    }

    // ************========  linear physics  ========************ \\

    /**
     * Accumulates the net linear force acting on this body.
     * @param theForce the force to apply
     */
    public void applyLinearForce(final Vector2 theForce) {
        // F = MA --> A = F / M
        myCache.set(theForce);
        myCache.div(myMass);
        myAcceleration.add(myCache);
    }

    /**
     * Sets the linear velocity of the rigid body. Velocity in verlet integration is done by changing the old
     * position vector.
     * @param theVelocity the new velocity vector
     */
    public void setLinearVelocity(final Vector2 theVelocity) {
        myCache.set(myPosition);
        myCache.sub(theVelocity);
        myOldPosition.set(myCache);
    }

    // ************========  rotational physics  ========************ \\

    /**
     * Accumulates the net torque acting on this body using a linear force and lever arm.
     * @param theLinearForce   the linear force to apply
     * @param thePointOfAction the length of the lever arm from the center of mass
     */
    public void applyTorque(final Vector2 theLinearForce, final Vector2 thePointOfAction) {
        // T = r x F
        // T = I * a --> a = T / I --> a = (r x F) / I
        // r is the lever arm from the force of action to the center of mass
        myCache.set(thePointOfAction);
        myCache.sub(myPosition);
        // negative to correct the spin
        myAngularAccel += -myCache.crossProduct(theLinearForce) / myMoi;
    }

    /**
     * Accumulates the net torque acting on this body using a torque.
     * @param theTorque the torque to apply
     */
    public void applyTorque(final double theTorque) {
        // T = I * a --> a = T / I
        myAngularAccel += theTorque / myMoi;
    }

    /**
     * Sets the angular position (orientation) of the body.
     * @param theOrientation the angular position in radians
     */
    public void setAngularPosition(final double theOrientation) {
        rotateBody(theOrientation);
        myOldAngularPos = myAngularPos;
    }

    /**
     * Sets the angular velocity of the body. Velocity in verlet integration is done by changing the old.
     * @param theAngVelocity the angular velocity in radians
     */
    public void setAngularVelocity(final double theAngVelocity) {
        myOldAngularPos = myAngularPos - theAngVelocity;
    }

    /**
     * Accumulates net impulse on this rigid body. For this, impulse is instantaneous force/torque. This is mainly
     * used to resolve bodies that are already overlapping.
     * @param theImpulseMag the impulse magnitude
     * @param theDirection  the direction of the impulse
     * @param theDistance   the distance from the center of mass to apply the impulse
     */
    public void applyImpulse(final double theImpulseMag, final Vector2 theDirection, final Vector2 theDistance) {
        final Vector2 jn = theDirection.mulNew(theImpulseMag);
        myImpulse.add(jn);
        myAngularImpulse += jn.crossProduct(theDistance);
    }

    /**
     * Updates the object using verlet integration. Assumes dt = 1.
     */
    protected void move() {
        // impulsive movement
        if (myImpulse.getX() != 0D || myImpulse.getY() != 0D) {
            setLinearVelocity(getLinearVelocity().addNew(myImpulse.divNew(myMass)));
        }
        if (myAngularImpulse != 0F) {
            setAngularVelocity(getAngularVelocity() + myAngularImpulse / myMoi);
        }

        // linear movement
        myCache.set(myPosition);
        translateBody(myPosition.subNew(myOldPosition).addNew(myAcceleration));
        myOldPosition.set(myCache);

        // angular movement
        double current = myAngularPos;
        rotateBody(myAngularPos - myOldAngularPos + myAngularAccel);
        myOldAngularPos = current;
    }

    /**
     * Update after everything else. Used to reset fields.
     */
    protected void postUpdate() {
        myAngularImpulse = 0;
        myImpulse.set(0, 0);
        myAngularAccel = 0;
        myAcceleration.set(0, 0);
        if (Math.abs(myAngularPos) > 10) {
            int adjustment = myAngularPos > 10 ? -10 : 10;
            myAngularPos += adjustment;
            myOldAngularPos += adjustment;
        }
    }

    /**
     * Rotates the rigid body about its center of mass by some radian.
     * @param theRadians the degree
     */
    private void rotateBody(final double theRadians) {
        for (final Vector2 v : myVertices) {
            VMath.rotate(v, myPosition, theRadians);
        }
        myAngularPos += theRadians;
    }

    /**
     * Translates the rigid body by a vector.
     * @param theTranslation the vector of translation
     */
    private void translateBody(final Vector2 theTranslation) {
        for (final Vector2 v : myVertices) {
            v.add(theTranslation);
        }
        myPosition.add(theTranslation);
    }

    /**
     * Translates the rigid body by a vector while keeping its velocity.
     * @param theTranslation the vector of translation
     */
    public void translate(final Vector2 theTranslation) {
        translateBody(theTranslation);
        myOldPosition.add(theTranslation);
    }

    // ======== getters ========

    /**
     * Gets the linear velocity of the body. For each infinite point in the rigid body, the linear velocity is
     * the same as the center of mass.
     * @return the linear velocity
     */
    public Vector2 getLinearVelocity() {
        return myPosition.subNew(myOldPosition);
    }

    /**
     * Gets the angular velocity of the body (how fast it is rotating).
     * @return
     */
    public double getAngularVelocity() {
        return myAngularPos - myOldAngularPos;
    }

    /**
     * Gets the velocity of the body at a point. This is done by scaling the perpendicular vector from the
     * center of mass to that point by the angular velocity.
     * @param thePoint the point
     * @return the velocity vector at that point
     */
    public Vector2 getLinearAngularVelocity(final Vector2 thePoint) {
        return thePoint.subNew(myPosition).perpNew().mulNew(getAngularVelocity());
    }

    /**
     * Gets the edges of the rigid body.
     * @return the edges as rigid body edges
     */
    public RigidBodyEdge[] getEdges() {
        return myEdges;
    }

    /**
     * Gets the vertices of the rigid body.
     * @return the vertices as vectors
     */
    public Vector2[] getVertices() {
        return myVertices;
    }

    /**
     * Gets the center of mass of the rigid body.
     * @return the center vector
     */
    public Vector2 getCenterOfMass() {
        return myPosition;
    }

    /**
     * Gets the orientation (the angular position) of the rigid body.
     * @return the orientation in radians
     */
    public double getOrientation() {
        return myAngularPos;
    }

    /**
     * Gets the mass of the rigid body.
     * @return the mass
     */
    public double getMass() {
        return myMass;
    }

    /**
     * Gets the moment of inertia of the rigid body at its center of mass.
     * @return the moment of inertia
     */
    public double getMoi() {
        return myMoi;
    }

    /**
     * Gets whether this body has physics
     * @return the physics
     */
    public boolean hasPhysics() {
        return hasPhysics;
    }
}
