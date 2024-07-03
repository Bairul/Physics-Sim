package com.survival.game.model;

import com.survival.game.utility.Vector2;

/**
 * Class for moving objects. This uses Semi-Implicit Euler integration
 */
public abstract class MovableObject extends GameObject {
    /** The velocity vector. */
    protected final Vector2 myVelocity;
    /** The acceleration vector. */
    protected final Vector2 myAcceleration;
    /** A reusable vector to attempt to save some memory. */
    private final Vector2 myCache;

    /**
     * Creates a movable object given the initial position and the object mass.
     * @param thePosition the position vector
     * @param theMass the mass
     */
    public MovableObject(Vector2 thePosition, float theMass) {
        super(thePosition, theMass);
        myVelocity = new Vector2();
        myAcceleration = new Vector2();
        myCache = new Vector2();
    }

    /**
     * Applies an impulse to this object. Impulse is force over time. One frame is 1 unit of time in
     * this simulation.
     * @param theForce the force as a vector to apply
     * @param theTime the time that the force is applied over
     */
    public void applyImpulse(final Vector2 theForce, final double theTime) {
        // F = MA
        // A = F / M
        myCache.set(theForce);
        myCache.div(myMass);

        // P = F * dT
        myCache.mul(theTime);
        myAcceleration.add(myCache);
    }

    /**
     * Moves using Semi-Implicit Euler integration. Not the best method but it's the simplest to start off.
     */
    public void move() {
        myVelocity.add(myAcceleration);
        myPosition.add(myVelocity);
    }

    /**
     * Update after everything else.
     */
    @Override
    public void postUpdate() {
        myAcceleration.set(0, 0);
    }

    /**
     * Gets the velocity vector.
     * @return the velocity
     */
    public Vector2 getVelocity() {
        return myVelocity;
    }

    public Vector2 getAcceleration() {
        return myAcceleration;
    }
}
