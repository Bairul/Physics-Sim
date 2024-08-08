package com.physicsim.game.utility;

import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.model.rigidbody.RigidBodyEdge;

import java.util.ArrayList;

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
     * @throws ArithmeticException if the slope is vertical
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
     * @return the intersection vector of the 2 line segments if it exists. Null otherwise
     */
    public static Vector2 intersect(final Vector2 theStartA, final Vector2 theEndA,
                                    final Vector2 theStartB, final Vector2 theEndB) {
        final Vector2 A = theEndA.subNew(theStartA);
        final Vector2 B = theEndB.subNew(theStartB);
        final Vector2 AB = theStartB.subNew(theStartA);

        final double denom = A.crossProduct(B);
        if (denom == 0) return null; // if denominator = 0, then the 2 line segments are parallel or co-linear

        final double t1 = AB.crossProduct(B) / denom;
        final double t2 = -1 * AB.crossProduct(A) / denom;

        // not intersecting but not parallel
        if (t1 < 0 || t1 > 1 || t2 < -1 || t2 > 0) return null;

        // reuse vector
        A.mul(t1);
        B.set(theStartA);
        B.add(A);

        return B;
    }

    /**
     * Computes the intersection point of a line segment and the circumference of a circle if it exists. The line
     * segment consists of a start and end vector while the circle consists of an origin and radius.
     * Returns empty array if no intersection, and an array of vectors for the 1 (tangential) or 2 intersections.
     * <br><br>
     * Uses the formula after for solving x by plugging in y=mx+b to x^2+y^2=r^2
     * @param theStart  the starting endpoint of the segment
     * @param theEnd    the ending endpoint of the segment
     * @param theOrigin the origin vector of the circle
     * @param theRadius the radius of the circle
     * @return the intersection vector(s) of the circle and line if it exists
     */
    public static Vector2[] intersect(final Vector2 theStart, final Vector2 theEnd,
                                      final Vector2 theOrigin, final double theRadius) {
        final ArrayList<Vector2> intersections = new ArrayList<>();
        // shifts line to by the circle's origin because this method assumes the circle is at the origin
        final Vector2 start = theStart.subNew(theOrigin);
        final Vector2 end = theEnd.subNew(theOrigin);

        try {
            final double m = slope(theStart, theEnd);
            final double b = -1 * m * start.getX() + start.getY(); // y-intercept
            final double c = m * m + 1;                            // some constant ¯\_(`-`)_/¯
            final double D = theRadius * theRadius * c - b * b;    // discriminant
            // if D negative, there is no intersection
            // if D is 0, there is only 1 intersection and that is the tangent
            if (D >= 0) {
                final double x_i = (-1 * m * b + Math.sqrt(D)) / c;      // circle-line formula for x
                final double y_i = m * x_i + b;
                start.set(x_i, y_i);             // reuse vector
                start.add(theOrigin);            // shift the point back
                intersections.add(start);
                // if D is positive, then there is 2 intersections on the circle
                if (D > 1) {
                    final double x_i2 = (-1 * m * b - Math.sqrt(D)) / c; // circle-line formula for x
                    final double y_i2 = m * x_i2 + b;
                    end.set(x_i2, y_i2);         // reuse vector
                    end.add(theOrigin);          // shift the point back
                    intersections.add(end);
                }
            }
        } catch (final ArithmeticException e) {
            // when the segment has a vertical slope
            final double D = theRadius * theRadius - start.getX() * start.getX();
            // if D negative, there is no intersection
            // if D is 0, there is only 1 intersection and that is the tangent
            if (D >= 0) {
                start.setY(Math.sqrt(D));        // reuse vector
                start.add(theOrigin);            // shift the point back
                intersections.add(start);
                // if D is positive, then there is 2 intersections on the circle
                if (D > 1) {
                    end.setY(-1 * Math.sqrt(D)); // reuse vector
                    end.add(theOrigin);          // shift the point back
                    intersections.add(end);
                }
            }
        }

        // remove points that are not on the line segment
        intersections.removeIf(intersection ->
                (intersection.getX() < theStart.getX() == intersection.getX() < theEnd.getX()) &&
                        (intersection.getY() < theStart.getY() == intersection.getY() < theEnd.getY()));

        return intersections.toArray(new Vector2[0]);
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

        final double m_p = -1 / m;
        final double x = theStart.getX();
        final double y = theStart.getY();

        final double x_i = (m * x - m_p * thePoint.getX() - y + thePoint.getY()) / (m - m_p);
        final double y_i = m * (x_i - x) + y;

        return new Vector2(2 * x_i - thePoint.getX(), 2 * y_i - thePoint.getY());
    }

    /**
     * Projects a point on to a line created by a starting point and ending point.
     *
     * @param theStart the starting endpoint
     * @param theEnd   the ending endpoint
     * @param thePoint the point to reflect over
     * @return the projected point
     */
    public static Vector2 project(final Vector2 theStart, final Vector2 theEnd, final Vector2 thePoint) {
        double m;

        try {
            m = slope(theStart, theEnd);
        } catch (final ArithmeticException e) {
            // project on to a vertical slope
            return new Vector2(theStart.getX(), thePoint.getY());
        }
        if (m == 0) {
            // project on to a horizontal slope
            return new Vector2(thePoint.getX(), theStart.getY());
        }

        final double m_p = -1 / m;
        final double x = theStart.getX();
        final double y = theStart.getY();

        final double x_i = (m * x - m_p * thePoint.getX() - y + thePoint.getY()) / (m - m_p);
        final double y_i = m * (x_i - x) + y;

        return new Vector2(x_i, y_i);
    }

    /**
     * Rotates a vector by some radian about an origin vector counter-clockwise.
     * @param theVector  the vector to rotate
     * @param theOrigin  the vector to rotate around
     * @param theRadians the radians of rotation
     */
    public static void rotate(final Vector2 theVector, final Vector2 theOrigin, final double theRadians) {
        /*
         * NOTE: The formula used is actually the clockwise rotation. However, in computer graphics
         * positive y points down, so a clockwise rotation becomes counter-clockwise and vice versa.
         */
        theVector.sub(theOrigin);
        theVector.set(theVector.getX() * Math.cos(theRadians) + theVector.getY() * Math.sin(theRadians),
                theVector.getY() * Math.cos(theRadians) - theVector.getX() * Math.sin(theRadians));
        theVector.add(theOrigin);
    }

    /**
     * Calculates the area of the polygon given an array of vertices.
     * @param theVertices the vertices as vectors (assumes the vertices are ordered counter-clockwise or clockwise)
     * @return the area of the polygon made from the vertices
     */
    public static double findArea(final Vector2[] theVertices) {
        double area = 0;
        for (int i = 0; i < theVertices.length; i++) {
            area += theVertices[i].crossProduct(theVertices[(i + 1) % theVertices.length]);
        }
        return area / 2;
    }

    /**
     * Gets the center of mass of an array of vertices. This is not the same as the average of all vertices.
     * @param theVertices the vertices as vectors (assumes the vertices are ordered counter-clockwise or clockwise)
     * @return the center of mass of the polygon aka centroid
     */
    public static Vector2 findCentroid(final Vector2[] theVertices) {
        final Vector2 centroid = new Vector2();
        final double c = 1 / (6 * findArea(theVertices));

        for (int i = 0; i < theVertices.length; i++) {
            final Vector2 nextVertex = theVertices[(i + 1) % theVertices.length];
            final double a = theVertices[i].crossProduct(nextVertex);

            centroid.add(theVertices[i].addNew(nextVertex).mulNew(a));
        }

        centroid.mul(c);
        return centroid;
    }

    /**
     * Gets the moment of inertia from a polygon given its vertices and mass. Assumes the polygon has a unifrom
     * mass distribution.
     * @param theVertices the vertices as vectors (assumes the vertices are ordered counter-clockwise or clockwise)
     * @param theMass the mass
     * @return the moment of inertia
     */
    public static double findMomentOfInertia(final Vector2[] theVertices, final double theMass) {
        final Vector2 G = findCentroid(theVertices);
        double inertia = 0;

        for (int i = 0; i < theVertices.length; i++) {
            final Vector2 nextVertex = theVertices[(i + 1) % theVertices.length];
            final double a = theVertices[i].crossProduct(nextVertex);

            final double b = theVertices[i].dotProduct(theVertices[i])
                           + theVertices[i].dotProduct(nextVertex)
                           + nextVertex.dotProduct(nextVertex);

            inertia += a * b;
        }

        inertia *= theMass / (12 * findArea(theVertices));

        return inertia - theMass * G.dotProduct(G);
    }

    /**
     * Gets the support vector from an array of vectors (vertices). The support vector is the farthest vector
     * in a given direction. Think of the direction vector as a plane that sweeps in that direction. This will
     * return the last vector (point). Time complexity is θ(V) where V is the number of vectors in the array.
     * @param theVectors   the vectors to find the support vector
     * @param theDirection the direction
     * @return the support vector
     */
    public static Vector2 findSupportVector(final Vector2[] theVectors, final Vector2 theDirection) {
        if (theVectors.length == 0) throw new IllegalArgumentException("Empty vector array");

        Vector2 bestVector = theVectors[0];
        double bestProjection = theVectors[0].dotProduct(theDirection);

        for (final Vector2 vector : theVectors) {
            double projection = vector.dotProduct(theDirection);

            if (projection > bestProjection) {
                bestProjection = projection;
                bestVector = vector;
            }
        }

        return bestVector;
    }

    // ********========= Rigid Body Stuff =========******** \\

    /**
     * Determines whether a point is contained in a rigid body. Uses ray casting algorithm
     * with a time complexity of θ(V) where V is the number of Vertices. In a polygon, the number of Edges
     * is equal to its Vertices.
     * @param theEdges the edges of the rigid body
     * @param thePoint the point
     * @return whether it is contained
     */
    public static boolean overlaps(final RigidBodyEdge[] theEdges, final Vector2 thePoint) {
        boolean count = false;
        for (final RigidBodyEdge e : theEdges) {
            if (e.rayCast(thePoint)) {
                count = !count;
            }
        }
        return count;
    }

    public static Vector2[] findAxisOfLeastPenetration(final RigidBody theA, final RigidBody theB) {
        final Vector2 point = new Vector2();
        final Vector2 normal = new Vector2();
        double bestProjection = Integer.MIN_VALUE;
        int bestIndex = 0;
        int index = 0;

        for (final RigidBodyEdge e : theA.getEdges()) {
            final Vector2 edgeNormalOfA = e.getPerp();
            final Vector2 support = findSupportVector(theB.getVertices(), edgeNormalOfA.mulNew(-1));

            double projection = support.subNew(e.getStart()).dotProduct(edgeNormalOfA);
            if (projection > bestProjection) {
                bestIndex = index;
                bestProjection = projection;
                point.set(support);
                normal.set(edgeNormalOfA);
            }
            index++;
        }

        if (bestProjection > 0) return new Vector2[] {};

        return new Vector2[] {point, normal, new Vector2(bestProjection, bestIndex)};
    }
}
