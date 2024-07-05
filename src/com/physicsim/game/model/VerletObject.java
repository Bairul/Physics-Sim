package com.physicsim.game.model;

import com.physicsim.game.utility.Vector2;

/**
 * This class uses verlet integration for the movement of game objects.
 *
 * @author Bairu Li
 */
public abstract class VerletObject extends GameObject {
    /** Constant for the sizing. */
    private static final int SIZE_SCALE = 4;
    /** The position vector. */
    protected final Vector2 myPosition;
    /** The previous position vector. */
    protected final Vector2 myOldPosition;
    /** The acceleration vector. */
    protected final Vector2 myAcceleration;
    /** A reusable vector to save some memory. */
    private final Vector2 myCache;
    /** The radius of the object. */
    protected double myRadius;
    /** The mass of the object. */
    protected double myMass;

    /**
     * Creates a game object given the initial position and mass and radius.
     *
     * @param thePosition position vector
     * @param theMass     mass
     * @param theRadius   radius of the object (set to 0 to scale with mass)
     */
    public VerletObject(final Vector2 thePosition, final double theMass, final double theRadius) {
        super();
        // vectors
        myPosition = new Vector2(thePosition);
        myOldPosition = new Vector2(thePosition);
        myAcceleration = new Vector2();
        myCache = new Vector2();

        myMass = theMass;
        // sets radius to be the root of the mass because of the area of a circle if it's less than 1
        myRadius = theRadius < 1 ? Math.sqrt(theMass) * SIZE_SCALE : theRadius;
    }

    /**
     * Update after everything else.
     */
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

    /**
     * Detects if the object is colliding with the edge of a rectangular boundary.
     * Bounces the object off of the boundary if it hits it.
     *
     * @param theBoundary the boundary as a vector
     */
    public void bounceOffBoundary(final Vector2 theBoundary) {
        myCache.set(myRadius, myRadius);
        theBoundary.sub(myCache);
        if (Math.abs(myPosition.getY()) > theBoundary.getY()) {
            myPosition.setY(2 * Math.signum(myPosition.getY()) * theBoundary.getY() - myPosition.getY());
            myOldPosition.setY(2 * Math.signum(myOldPosition.getY()) * theBoundary.getY() - myOldPosition.getY());
        }
        if (Math.abs(myPosition.getX()) > theBoundary.getX()) {
            myPosition.setX(2 * Math.signum(myPosition.getX()) * theBoundary.getX() - myPosition.getX());
            myOldPosition.setX(2 * Math.signum(myOldPosition.getX()) * theBoundary.getX() - myOldPosition.getX());
        }
        theBoundary.add(myCache);
    }

    /**
     * Sets the velocity of the object. Velocity in verlet integration is done by using changing the old
     * position vector.
     * @param theVelocity the velocity vector
     */
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
