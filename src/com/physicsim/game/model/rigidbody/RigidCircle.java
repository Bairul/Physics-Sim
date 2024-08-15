package com.physicsim.game.model.rigidbody;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.visitor.GameObjectVisitor;

/**
 * Class for a rigid circle physics game object.
 */
public class RigidCircle extends GameObject {
    /** The radius of the circle. */
    private final double myRadius;
    /** Reusable vector to save some memory. */
    private final Vector2 myCache;
    // physics fields
    /** The uniform mass of the rigid body. */
    private final double myMass;
    /** The position vector but also the center of mass and the origin of the circle. */
    private final Vector2 myPosition;
    // Linear components
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
     * Constructs a circle given the origin and the radius.
     * @param theOrigin the origin
     * @param theRadius the radius (must be positive)
     * @throws IllegalArgumentException if radius is a non-positive
     */
    public RigidCircle(final Vector2 theOrigin, final double theRadius, final double theMass) {
        if (theRadius <= 0) throw new IllegalArgumentException("radius must be positive");
        myRadius = theRadius;
        myMass = theMass;
        myMoi = myRadius * myRadius * myMass / 2;
        myCache = new Vector2();

        myPosition = new Vector2(theOrigin);
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
     * Rotates the angular position (orientation) of the body by some radian.
     * @param theOrientation the angular position in radians
     */
    public void rotate(final double theOrientation) {
        myAngularPos += theOrientation;
        myOldAngularPos += theOrientation;
    }

    /**
     * Sets the angular velocity of the body. Velocity in verlet integration is done by changing the old.
     * @param theAngVelocity the angular velocity in radians
     */
    public void setAngularVelocity(final double theAngVelocity) {
        myOldAngularPos = myAngularPos - theAngVelocity;
    }

    /**
     * Accumulates net impulse on this rigid body. Impulse is instantaneous force/torque. This is mainly
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
        myPosition.add(myPosition.subNew(myOldPosition).addNew(myAcceleration));
        myOldPosition.set(myCache);

        // angular movement
        double current = myAngularPos;
        myAngularPos += myAngularPos - myOldAngularPos + myAngularAccel;
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
        if (Math.abs(myAngularPos) > VMath.PI_2) {
            double adjustment = -VMath.PI_2 * Math.signum(myAngularPos);
            myAngularPos += adjustment;
            myOldAngularPos += adjustment;
        }
    }

    @Override
    public void update() {
        if (!hasPhysics) return;
        // must have
        move();

        // must have
        postUpdate();
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
     * Gets the radius of the circle.
     * @return the radius
     */
    public double getRadius() {
        return myRadius;
    }

    /**
     * Gets the diameter of the circle as an integer. Useful for graphics.
     * @return the diameter
     */
    public int getDiameter() {
        return Math.round((float) myRadius * 2F);
    }

    /**
     * Gets the center of mass (origin) vector of the circle.
     * @return the origin
     */
    public Vector2 getCenterOfMass() {
        return myPosition;
    }

    /**
     * Gets the orientation (the angular position) of the rigid body.
     * @return the orientation in radians
     */
    public Vector2 getOrientationVector() {
        myCache.set(myRadius, 0);
        VMath.rotate(myCache, new Vector2(), myAngularPos);
        return myPosition.addNew(myCache);
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

    @Override
    public <V> V accept(final GameObjectVisitor<V> theV) {
        return theV.visit(this);
    }
}
