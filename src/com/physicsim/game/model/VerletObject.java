package com.physicsim.game.model;

import com.physicsim.game.utility.Vector2;

public abstract class VerletObject extends GameObject {
    /** Constant for the sizing. */
    private static final int SIZE_SCALE = 5;
    /** The position vector. */
    protected Vector2 myPosition;
    /** The radius of the object. */
    protected float myRadius;
    /** The mass of the object. */
    protected double myMass;
    /** The old position vector. */
    protected Vector2 myOldPosition;
    /** The acceleration vector. */
    protected Vector2 myAcceleration;
    /** A reusable vector to save some memory. */
    private Vector2 myCache;

    /**
     * Creates a game object given the initial position and mass.
     *
     * @param thePosition position vector
     * @param theMass     mass
     */
    public VerletObject(final Vector2 thePosition, final double theMass) {
        super();
        // vectors
        myPosition = new Vector2(thePosition);
        myOldPosition = new Vector2(thePosition);
        myAcceleration = new Vector2();
        myCache = new Vector2();

        myMass = theMass;
        // sets radius to be the root of the mass because of the area of a circle
        myRadius = (float) Math.sqrt(theMass) * SIZE_SCALE;
    }

    /**
     * Update after everything else.
     */
    @Override
    protected void postUpdate() {
        myAcceleration.set(0, 0);
    }

    /**
     * Updates the object using verlet integration.
     */
    protected void move() {
        myCache.set(myPosition);
        myPosition.add(myPosition.subNew(myOldPosition).addNew(myAcceleration));
        myOldPosition.set(myCache);
    }

    /**
     * Applies an impulse to this object. Impulse is force over time. One frame is 1 unit of time in
     * this simulation.
     * @param theForce the force as a vector to apply
     */
    public void applyForce(final Vector2 theForce) {
        // F = MA
        // A = F / M
        myAcceleration.add(theForce.divNew(myMass));
    }

    public void setVelocity(final Vector2 theVelocity) {
        myCache.set(myPosition);
        myCache.sub(theVelocity);
        myOldPosition.set(myCache);
    }

    /**
     * Gets the velocity of the object by subtracting the current and old position.
     * @return the velocity vector
     */
    public Vector2 getVelocity() {
        return myPosition.subNew(myOldPosition);
    }

    /**
     * Gets the position: the x and y coordinate of the object.
     * @return the position vector
     */
    public Vector2 getPosition() {
        return myPosition;
    }

    /**
     * Gets the old position: the x and y coordinate of the object previously.
     * @return the old position vector
     */
    public Vector2 getOldPosition() {
        return myOldPosition;
    }

    /**
     * Gets the object's mass.
     * @return the mass
     */
    public double getMass() {
        return myMass;
    }

    /**
     * Gets the radius of the object.
     * @return the radius as a int
     */
    public int getRadius() {
        return (int) myRadius;
    }

    /**
     * Gets the diameter.
     * @return the diameter as a int
     */
    public int getDiameter() {
        return (int) (myRadius * 2);
    }
}
