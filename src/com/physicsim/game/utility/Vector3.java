package com.physicsim.game.utility;

/**
 * My own vector class.
 *
 * @author Bairu Li
 */
public class Vector3 {
    /** The x component. */
    protected double myX;
    /** The y component. */
    protected double myY;
    /** The z component. */
    protected double myZ;

    /**
     * Constructs a vector given the x, y, and z component
     * @param theX the x (double)
     * @param theY the y (double)
     */
    public Vector3(final double theX, final double theY, final double theZ) {
        set(theX, theY, theZ);
    }

    /**
     * Constructs a vector given another vector. This copies the x and y components.
     * @param theV the other vector
     */
    public Vector3(final Vector3 theV) {
        if (theV == null)
            set(0, 0, 0);
        else
            set(theV.getX(), theV.getY(), theV.getZ());
    }

    /**
     * Default constructor when no parameters are given. (0, 0)
     */
    public Vector3() {
        this(0, 0, 0);
    }

    // ============ vector math ============

    /**
     * Adds another vector.
     * @param theV the other vector
     */
    public void add(final Vector3 theV) {
        myX += theV.myX;
        myY += theV.myY;
        myZ += theV.myZ;
    }

    /**
     * Subtracts another vector.
     * @param theV the other vector
     */
    public void sub(final Vector3 theV) {
        myX -= theV.myX;
        myY -= theV.myY;
        myZ -= theV.myZ;
    }

    /**
     * Multiplies by a scalar.
     * @param theScalar the scalar
     */
    public void mul(final double theScalar) {
        myX *= theScalar;
        myY *= theScalar;
        myZ *= theScalar;
    }

    /**
     * Divides by a scalar.
     * @param theScalar the scalar
     */
    public void div(final double theScalar) {
        myX /= theScalar;
        myY /= theScalar;
        myZ /= theScalar;
    }

    /**
     * Converts this vector to a unit vector by normalizing it.
     */
    public void norm() {
        div(getMagnitude());
    }


    // === vector math with return ===

    /**
     * Adds another vector to this vector and return a new vector. This vector remains unaffected.
     * @param theV the other vector
     * @return the sum of the 2 vectors
     */
    public Vector3 addNew(final Vector3 theV) {
        return new Vector3(myX + theV.myX, myY + theV.myY, myZ + theV.myZ);
    }

    /**
     * Subtracts another vector to this vector and return a new vector. This vector remains unaffected.
     * @param theV the other vector
     * @return the difference of the 2 vectors
     */
    public Vector3 subNew(final Vector3 theV) {
        return new Vector3(myX - theV.myX, myY - theV.myY, myZ - theV.myZ);
    }

    /**
     * Multiplies by a scalar to this vector and return a new vector. This vector remains unaffected.
     * @param theScalar the scalar
     * @return the scalar product
     */
    public Vector3 mulNew(final double theScalar) {
        return new Vector3(myX * theScalar, myY * theScalar, myZ * theScalar);
    }

    /**
     * Divides by a scalar to this vector and return a new vector. This vector remains unaffected.
     * @param theScalar the scalar
     * @return the scalar quotient
     */
    public Vector3 divNew(final double theScalar) {
        return new Vector3(myX / theScalar, myY / theScalar, myZ / theScalar);
    }

    /**
     * Converts this vector to a unit vector and return a new vector. This vector remains unaffected.
     * @return the unit vector
     */
    public Vector3 normNew() {
        return divNew(getMagnitude());
    }

    // ===== other math =====

    /**
     * Computes the magnitude (length to the origin: 0,0) of the vector.
     * @return the magnitude as a double
     */
    public double getMagnitude() {
        return Math.sqrt(myX * myX + myY * myY + myZ * myZ);
    }

    // ============ getters ============

    /**
     * Gets the x component as a double.
     * @return the x (double)
     */
    public double getX() { return myX; }

    /**
     * Gets the y component as a double.
     * @return the y (double)
     */
    public double getY() {
        return myY;
    }

    /**
     * Gets the z component as a double.
     * @return the z (double)
     */
    public double getZ() {
        return myZ;
    }

    /**
     * Gets the x component as an int.
     * @return the x (int)
     */
    public int intX() {
        return (int) myX;
    }

    /**
     * Gets the y component as an int.
     * @return the y (int)
     */
    public int intY() {
        return (int) myY;
    }

    /**
     * Gets the z component as an int.
     * @return the z (int)
     */
    public int intZ() {
        return (int) myZ;
    }

    // ============ setters ============

    // set floats
    /**
     * Sets the x component when given a double.
     * @param theX the new x (double)
     */
    public void setX(final double theX) {
        myX = theX;
    }

    /**
     * Sets the y component when given a double.
     * @param theY the new y (double)
     */
    public void setY(final double theY) {
        myY = theY;
    }

    /**
     * Sets the z component when given a double.
     * @param theZ the new z (double)
     */
    public void setZ(final double theZ) {
        myY = theZ;
    }

    /**
     * Sets both the x and y components when given double.
     * @param theX the new x (double)
     * @param theY the new y (double)
     * @param theZ the new z (double)
     */
    public void set(final double theX, final double theY, final double theZ) {
        setX(theX);
        setY(theY);
        setZ(theZ);
    }

    // set vector
    /**
     * Sets both the x and y components when given a vector.
     * @param theV the vector to copy from
     */
    public void set(final Vector3 theV) {
        set(theV.getX(), theV.getY(), theV.getZ());
    }

    /**
     * Returns the current state of the vector as a string.
     * @return the x and y values in a string
     */
    @Override
    public String toString() {
        return "(" + myX + ", " + myY + ", " + myZ + ")";
    }
}
