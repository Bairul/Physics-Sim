package com.survival.game.model;

import com.survival.game.utility.Vector2;

public abstract class MovableObject extends GameObject {
    private static final float LOW_VELOCITY_TOLERANCE = 0.01F;
    protected final Vector2 myVelocity;
    protected final Vector2 myAcceleration;
    private final Vector2 myCache;
    private final float myMass;
    private boolean limitVelocity;
    private float myVelocityLimit;

    public MovableObject(Vector2 thePosition, float theMass) {
        super(thePosition, (float) Math.sqrt(theMass));
        myVelocity = new Vector2();
        myAcceleration = new Vector2();
        myCache = new Vector2();
        myMass = theMass;
    }

    public void applyImpulse(final Vector2 theForce, final float theTime) {
        // F = MA
        // A = F / M
        myCache.set(theForce);
        myCache.div(myMass);

        myCache.mul(theTime);
        myAcceleration.add(myCache);
    }

    public void setLimitVelocity(final boolean theLimit, final float theVelocity) {
        limitVelocity = theLimit;
        myVelocityLimit = theVelocity;
    }

    public void move() {
        myVelocity.add(myAcceleration);
        myPosition.add(myVelocity);
    }

    @Override
    public void postUpdate() {
        myAcceleration.set(0, 0);
    }

    public Vector2 getVelocity() {
        return myVelocity;
    }
}
