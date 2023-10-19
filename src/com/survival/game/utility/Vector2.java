package com.survival.game.utility;

public class Vector2 {
    private float myX;
    private float myY;
    public Vector2(final float theX, final float theY) {
        set(theX, theY);
    }
    public Vector2(final Vector2 theV) {
        set(theV.getX(), theV.getY());
    }

    public Vector2(final double theX, final double theY) {
        set(theX, theY);
    }

    public Vector2() {
        this(0, 0);
    }

    // ============ vector math ============
    public void add(Vector2 theV) {
        myX += theV.myX;
        myY += theV.myY;
    }

    public void sub(Vector2 theV) {
        myX -= theV.myX;
        myY -= theV.myY;
    }

    public void mul(float theScalar) {
        myX *= theScalar;
        myY *= theScalar;
    }

    public void normalize() {
        float mag = getMagnitude();
        myX /= mag;
        myY /= mag;
    }

    public float getMagnitude() {
        return (float) Math.sqrt(myX * myX + myY * myY);
    }

    // ============ getters ============
    public float getX() { return myX; }

    public float getY() {
        return myY;
    }

    public int intX() {
        return Math.round(myX);
    }

    public int intY() {
        return Math.round(myY);
    }

    // ============ setters ============

    // set floats
    public void setX(final float theX) {
        myX = theX;
    }

    public void setY(final float theY) {
        myY = theY;
    }

    public void set(final float theX, final float theY) {
        setX(theX);
        setY(theY);
    }

    // set doubles
    public void setX(final double theX) {
        myX = (float) theX;
    }

    public void setY(final double theY) {
        myY = (float) theY;
    }

    public void set(final double theX, final double theY) {
        setX(theX);
        setY(theY);
    }

    // set vector
    public void set(final Vector2 theV) {
        set(theV.getX(), theV.getY());
    }

    @Override
    public String toString() {
        return "(" + myX + ", " + myY + ")";
    }

    public static void linearInterpolationPoint(final Vector2 theA, final Vector2 theB, final Vector2 theReturnVector) {
        if (theReturnVector.getX() == 0) {
            theReturnVector.setX((theB.getX() - theA.getX()) / (theB.getY() - theA.getY()) * (theReturnVector.getY() - theA.getY()) + theA.getX());
        } else {
            theReturnVector.setY((theB.getY() - theA.getY()) / (theB.getX() - theA.getX()) * (theReturnVector.getX() - theA.getX()) + theA.getY());
        }
    }

    public static float linearInterpolationTime(final Vector2 theA, final Vector2 theB, final Vector2 theBoundary, final float theRadius, final boolean isY) {
        if (isY) return (theBoundary.getY() - theRadius - theA.getY()) / (theB.getY() - theA.getY());
        return (theBoundary.getX() + theRadius - theA.getX()) / (theB.getX() - theA.getX());
    }
}
