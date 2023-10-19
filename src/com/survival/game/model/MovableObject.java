package com.survival.game.model;

import com.survival.game.utility.Vector2;

public abstract class MovableObject extends GameObject {
    private static final float LOW_VELOCITY_TOLERANCE = 0.01F;
    protected final Vector2 myVelocity;
    protected final Vector2 myAcceleration;
    private final Vector2 myCache;
    private boolean limitVelocity;
    private float myVelocityLimit;

    public MovableObject(Vector2 thePosition, float theSize) {
        super(thePosition, theSize);
        myVelocity = new Vector2();
        myAcceleration = new Vector2();
        myCache = new Vector2();
    }

    public void applyImpulse(final Vector2 theForce, final float theTime) {
        myCache.set(theForce);
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

    public void postUpdate() {
        myAcceleration.set(0, 0);
    }

    public Vector2 getVelocity() {
        return myVelocity;
    }
}
