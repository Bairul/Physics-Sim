package com.physicsim.game.model.rigidbody;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.model.GameWorld;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;

public abstract class Rigid2D extends GameObject {
    /** Reusable vector to save some memory. */
    protected final Vector2 myCache;
    // physics fields
    /** The uniform mass of the rigid body. */
    protected final double myMass;
    // Linear components
    /** The position vector but also the center of mass. */
    protected final Vector2 myPosition;
    /** The old position to use verlet integration. */
    protected final Vector2 myOldPosition;
    /** The Net acceleration vector. */
    protected final Vector2 myAcceleration;
    /** The Net impulse vector. */
    protected final Vector2 myImpulse;
    // Rotational components
    // In 2D space, angular components are scalars not vectors
    /** The moment of inertia at the center of mass. */
    protected double myMoi;
    /** The angular position (orientation), measured in radians. */
    protected double myAngularPos;
    /** The old angular position. */
    protected double myOldAngularPos;
    /** The net angular acceleration. */
    protected double myAngularAccel;
    /** The net angular impulse. */
    protected double myAngularImpulse;
    protected double myGhostAngularVel;
    private Vector2 myGhostVelocity;
    /** Whether physics applies to this body. Distinguishes static bodies.*/
    protected boolean hasDynamics;
    protected int myCollisions;

    public Rigid2D(final Vector2 theCenterOfMass, final double theMass) {
        myMass = theMass;
        myCache = new Vector2();

        // initialize linear components
        myPosition = new Vector2(theCenterOfMass);
        myOldPosition = new Vector2(myPosition);
        myAcceleration = new Vector2();
        myImpulse = new Vector2();
        myGhostVelocity = new Vector2();
    }

    /**
     * Enables/Disables the dynamics of this body. By Default the dynamics are disabled. When disabled, it will be
     * treated as a static object that can still be interacted with.
     * @param theDynamics sets the physics
     */
    public void setDynamics(final boolean theDynamics) {
        hasDynamics = theDynamics;
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

    public void applyGravity() {
        myCache.set(GameWorld.GRAVITY);
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

    /**
     * Translates the rigid body by a vector while keeping its velocity.
     * @param theTranslation the vector of translation
     */
    public abstract void translate(final Vector2 theTranslation);

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
     * Sets the angular velocity of the body. Velocity in verlet integration is done by changing the old.
     * @param theAngVelocity the angular velocity in radians
     */
    public void setAngularVelocity(final double theAngVelocity) {
        myOldAngularPos = myAngularPos - theAngVelocity;
    }

    /**
     * Rotates the angular position (orientation) of the body by some radian.
     * @param theOrientation the angular position in radians
     */
    public abstract void rotate(final double theOrientation);

    // ************========  impulsive physics  ========************ \\

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
        final double angImp = jn.crossProduct(theDistance);
        myAngularImpulse += angImp;

        myGhostAngularVel += getAngularVelocity() + angImp / myMoi;
    }

    @Override
    public void update() {
        if (!hasDynamics) return;
        preMove();
        move();
        postMove();
    }

    /**
     * Updates the object using verlet integration. Assumes dt = 1.
     */
    abstract void move();

    /**
     * Update before everything else. Used for impulses.
     */
    protected void preMove() {
        // impulsive movement
        if (myImpulse.getX() != 0D || myImpulse.getY() != 0D) {
            setLinearVelocity(getLinearVelocity().addNew(myImpulse.divNew(myMass)));
        }
        if (myAngularImpulse != 0F) {
            setAngularVelocity(getAngularVelocity() + myAngularImpulse / myMoi);
        }
    }

    /**
     * Update after everything else. Used to reset fields.
     */
    protected void postMove() {
        myGhostAngularVel = 0;
        myAngularImpulse = 0;
        myImpulse.set(0, 0);
        myAngularAccel = 0;
        myAcceleration.set(0, 0);
        myGhostVelocity.set(0, 0);
        myCollisions = 0;
        if (Math.abs(myAngularPos) > VMath.PI_2) {
            double adjustment = -VMath.PI_2 * Math.signum(myAngularPos);
            myAngularPos += adjustment;
            myOldAngularPos += adjustment;
        }
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
     * @return the angular velocity
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
     * Gets the mass of the rigid body divided by the number of collisions.
     * @return the split mass
     */
    public double getSplitMass() {
        if (myCollisions == 0) return  myMass;
        return myMass / myCollisions;
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
    public boolean hasDynamics() {
        return hasDynamics;
    }

    public void incCollision() {
        if (hasDynamics) myCollisions++;
    }
}
