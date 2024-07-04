package com.physicsim.game.model;

import com.physicsim.game.utility.Vector2;

public abstract class VerletObject extends GameObject {

    protected Vector2 myOldPosition;
    protected Vector2 myAcceleration;
    private Vector2 myCache;

    /**
     * Creates a game object given the initial position and mass.
     *
     * @param thePosition position vector
     * @param theMass     mass
     */
    public VerletObject(Vector2 thePosition, float theMass) {
        super(thePosition, theMass);
        myOldPosition = new Vector2(thePosition);
        myAcceleration = new Vector2();
        myCache = new Vector2();
    }

    protected void move() {
        myCache.set(myPosition);
        myPosition.add(myPosition.subNew(myOldPosition).addNew(myAcceleration));
        myOldPosition.set(myCache);
    }

    /**
     * Update after everything else.
     */
    @Override
    protected void postUpdate() {
        myAcceleration.set(0, 0);
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

    public Vector2 getOldPosition() {
        return myOldPosition;
    }

    public void setVelocity(final Vector2 theVelocity) {
        myCache.set(myPosition);
        myCache.sub(theVelocity);
        myOldPosition.set(myCache);
    }

    public Vector2 getVelocity() {
        return myPosition.subNew(myOldPosition);
    }
}
