package com.survival.game.utility;

/**
 * My own vector class.
 */
public class Vector2 {
    /** The x component. */
    private float myX;
    /** The y component. */
    private float myY;

    /**
     * Constructs a vector given the x and y component as a float.
     * @param theX the x (float)
     * @param theY the y (float)
     */
    public Vector2(final float theX, final float theY) {
        set(theX, theY);
    }

    /**
     * Constructs a vector given the x and y component as a double.
     * @param theX the x (double)
     * @param theY the y (double)
     */
    public Vector2(final double theX, final double theY) {
        set(theX, theY);
    }

    /**
     * Constructs a vector given another vector. This copies the x and y components.
     * @param theV the other vector
     */
    public Vector2(final Vector2 theV) {
        if (theV == null)
            set(0, 0);
        else
            set(theV.getX(), theV.getY());
    }

    /**
     * Default constructor when no parameter is given.
     */
    public Vector2() {
        this(0, 0);
    }

    // ============ vector math ============

    /**
     * Adds another vector.
     * @param theV the other vector
     */
    public void add(final Vector2 theV) {
        myX += theV.myX;
        myY += theV.myY;
    }

    /**
     * Subtracts another vector.
     * @param theV the other vector
     */
    public void sub(final Vector2 theV) {
        myX -= theV.myX;
        myY -= theV.myY;
    }

    /**
     * Multiplies by a scalar.
     * @param theScalar the scalar
     */
    public void mul(final float theScalar) {
        myX *= theScalar;
        myY *= theScalar;
    }

    /**
     * Divides by a scalar.
     * @param theScalar the scalar
     */
    public void div(final float theScalar) {
        myX /= theScalar;
        myY /= theScalar;
    }

    /**
     * Converts this vector to a unit vector.
     */
    public void normalize() {
        div(getMagnitude());
    }

    /**
     * Computes the dot product between 2 vecotrs.
     * @param theOther the other vector to take the dot product of
     * @return the dot product
     */
    public float dotProduct(final Vector2 theOther) {
        return myX * theOther.myX + myY * theOther.myY;
    }

    /**
     * Gets the magnitude (length to the origin: 0,0) of the vector.
     * @return the magnitude as a float
     */
    public float getMagnitude() {
        return (float) Math.sqrt(myX * myX + myY * myY);
    }

    // ============ getters ============

    /**
     * Gets the x component as a float.
     * @return the x
     */
    public float getX() { return myX; }

    /**
     * Gets the y component as a float.
     * @return the y
     */
    public float getY() {
        return myY;
    }

    /**
     * Gets the x component as an int.
     * @return the x
     */
    public int intX() {
        return Math.round(myX);
    }

    /**
     * Gets the y component as an int.
     * @return the y
     */
    public int intY() {
        return Math.round(myY);
    }

    // ============ setters ============

    // set floats
    /**
     * Sets the x component when given a float.
     * @param theX the new x (float)
     */
    public void setX(final float theX) {
        myX = theX;
    }

    /**
     * Sets the y component when given a float.
     * @param theY the new y (float)
     */
    public void setY(final float theY) {
        myY = theY;
    }

    /**
     * Sets both the x and y components when given floats.
     * @param theX the new x (float)
     * @param theY the new y (float)
     */
    public void set(final float theX, final float theY) {
        setX(theX);
        setY(theY);
    }

    // set doubles
    /**
     * Sets the x component when given a double.
     * @param theX the new x (double)
     */
    public void setX(final double theX) {
        myX = (float) theX;
    }

    /**
     * Sets the y component when given a double.
     * @param theY the new y (double)
     */
    public void setY(final double theY) {
        myY = (float) theY;
    }

    /**
     * Sets both the x and y components when given doubles.
     * @param theX the new x (double)
     * @param theY the new y (double)
     */
    public void set(final double theX, final double theY) {
        setX(theX);
        setY(theY);
    }

    // set vector
    /**
     * Sets both the x and y components when given a vector.
     * @param theV the vector to copy from
     */
    public void set(final Vector2 theV) {
        set(theV.getX(), theV.getY());
    }

    /**
     * Returns the current state of the vector as a string.
     * @return the x and y values in a string
     */
    @Override
    public String toString() {
        return "(" + myX + ", " + myY + ")";
    }
}
