package com.physicsim.game.utility;

/**
 * My own vector class.
 */
public class Vector2 {
    /** The x component. */
    private double myX;
    /** The y component. */
    private double myY;

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
    public void mul(final double theScalar) {
        myX *= theScalar;
        myY *= theScalar;
    }

    /**
     * Divides by a scalar.
     * @param theScalar the scalar
     */
    public void div(final double theScalar) {
        myX /= theScalar;
        myY /= theScalar;
    }

    /**
     * Converts this vector to a unit vector.
     */
    public void norm() {
        div(getMagnitude());
    }

    // === vector math with return ===

    /**
     * Adds another vector to this vector. This vector remains unaffected.
     * @param theV the other vector
     * @return the sum of the 2 vectors
     */
    public Vector2 addNew(final Vector2 theV) {
        return new Vector2(myX + theV.myX, myY + theV.myY);
    }

    /**
     * Subtracts another vector to this vector. This vector remains unaffected.
     * @param theV the other vector
     * @return the difference of the 2 vectors
     */
    public Vector2 subNew(final Vector2 theV) {
        return new Vector2(myX - theV.myX, myY - theV.myY);
    }

    /**
     * Multiplies by a scalar to this vector. This vector remains unaffected.
     * @param theScalar the scalar
     * @return the scalar product
     */
    public Vector2 mulNew(final double theScalar) {
        return new Vector2(myX * theScalar, myY * theScalar);
    }

    /**
     * Divides by a scalar to this vector. This vector remains unaffected.
     * @param theScalar the scalar
     * @return the scalar quotient
     */
    public Vector2 divNew(final double theScalar) {
        return new Vector2(myX / theScalar, myY / theScalar);
    }

    /**
     * Converts this vector to a unit vector. This vector remains unaffected.
     * @return the unit vector
     */
    public Vector2 normNew() {
        return divNew(getMagnitude());
    }

    /**
     * Computes the dot product between 2 vectors.
     * @param theOther the other vector to take the dot product of
     * @return the dot product
     */
    public double dotProduct(final Vector2 theOther) {
        return myX * theOther.myX + myY * theOther.myY;
    }

    /**
     * Gets the magnitude (length to the origin: 0,0) of the vector.
     * @return the magnitude as a float
     */
    public double getMagnitude() {
        return (float) Math.sqrt(myX * myX + myY * myY);
    }

    public double getDistance(final Vector2 theOther) {
        double dx = theOther.myX - myX;
        double dy = theOther.myY - myY;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // ============ getters ============

    /**
     * Gets the x component as a float.
     * @return the x (float)
     */
    public double getX() { return myX; }

    /**
     * Gets the y component as a float.
     * @return the y (float)
     */
    public double getY() {
        return myY;
    }

    /**
     * Gets the x component as an int.
     * @return the x (int)
     */
    public int intX() {
        return Math.round((float) myX);
    }

    /**
     * Gets the y component as an int.
     * @return the y (int)
     */
    public int intY() {
        return Math.round((float) myY);
    }

    // ============ setters ============

    // set floats
    /**
     * Sets the x component when given a float.
     * @param theX the new x (float)
     */
    public void setX(final double theX) {
        myX = theX;
    }

    /**
     * Sets the y component when given a float.
     * @param theY the new y (float)
     */
    public void setY(final double theY) {
        myY = theY;
    }

    /**
     * Sets both the x and y components when given floats.
     * @param theX the new x (float)
     * @param theY the new y (float)
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