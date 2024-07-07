package com.physicsim.game.utility;

/**
 * Utility class for vector mathematics.
 */
public final class VMath {
    /** Private constructor to prevent instantiation. */
    private VMath() {}

    /**
     * Computes the slope of 2 vectors.
     *
     * @param theStart the starting endpoint
     * @param theEnd   the ending enpoint
     * @return the slope
     */
    public static double slope(final Vector2 theStart, final Vector2 theEnd) {
        if (theEnd.getX() == theStart.getX()) throw new ArithmeticException("Divide by 0");
        return (theEnd.getY() - theStart.getY()) / (theEnd.getX() - theStart.getX());
    }

    /**
     * Computes the linear interpolation of a line segment for the y value given the x value. The line segment
     * consists of 2 vectors: the start and the end.
     *
     * @param theStart the starting endpoint
     * @param theEnd   the ending endpoint
     * @param theX     the x value to compute for the y
     * @return the linear interpolation
     */
    public static double lerpY(final Vector2 theStart, final Vector2 theEnd, final double theX) {
        return theStart.getY() + (theX - theStart.getX()) * slope(theStart, theEnd);
    }

    /**
     * Computes the intersection point of 2 line segments if it exists. Both line segment A and B consists of a
     * start and end vector. Returns null if the 2 lines are parallel, co-linear, or no intersection.
     *
     * @param theStartA the starting endpoint of A
     * @param theEndA   the ending endpoint of A
     * @param theStartB the starting endpoint of B
     * @param theEndB   the ending endpoint of B
     * @return the intersection vector of the 2 line segments if it exists. Null otherwise.
     */
    public static Vector2 intersect(final Vector2 theStartA, final Vector2 theEndA,
                                    final Vector2 theStartB, final Vector2 theEndB) {
        final Vector2 A = theEndA.subNew(theStartA);
        final Vector2 B = theEndB.subNew(theStartB);
        final Vector2 AB = theStartB.subNew(theStartA);

        double denom = A.crossProduct(B);
        if (denom == 0) return null; // if denominator = 0, then the 2 line segments are parallel or co-linear

        double t1 = AB.crossProduct(B) / denom;
        double t2 = -1 * AB.crossProduct(A) / denom;

        // if the |t| is > 1 then it is not intersecting
        if (Math.abs(t1) > 1 || Math.abs(t2) > 1) return null;

        // reuse vector
        A.mul(t1);
        B.set(theStartA);
        B.add(A);

        return B;
    }

    /**
     * Reflects a point over a line created by a starting point and ending point.
     *
     * @param theStart the starting endpoint
     * @param theEnd   the ending endpoint
     * @param thePoint the point to reflect over
     * @return the reflected point
     */
    public static Vector2 reflect(final Vector2 theStart, final Vector2 theEnd, final Vector2 thePoint) {
        double m;

        try {
            m = slope(theStart, theEnd);
        } catch (final ArithmeticException e) {
            // reflect over a vertical slope
            return new Vector2(2 * theStart.getX() - thePoint.getX(), thePoint.getY());
        }
        if (m == 0) {
            // reflect over a horizontal slope
            return new Vector2(thePoint.getX(), 2 * theStart.getY() - thePoint.getY());
        }

        double m_p = -1 / m;
        double x = theStart.getX();
        double y = theStart.getY();

        double x_i = (m * x - m_p * thePoint.getX() - y + thePoint.getY()) / (m - m_p);
        double y_i = m * (x_i - x) + y;

        return new Vector2(2 * x_i - thePoint.getX(), 2 * y_i - thePoint.getY());
    }
}
