package com.physicsim.game.model.rigidbody;

import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;

/**
 * Class for an edge of a rigid body. Stores a starting and ending vector that forms a line. The vectors
 * are references.
 */
public final class RigidBodyEdge {
    /** The start vector of the edge. */
    private final Vector2 myStart;
    /** The end vector of the edge. */
    private final Vector2 myEnd;

    /**
     * Creates an edge with a start ane end point.
     * @param theStart the start vector
     * @param theEnd   the end vector
     */
    public RigidBodyEdge(final Vector2 theStart, final Vector2 theEnd) {
        myStart = theStart;
        myEnd = theEnd;
    }

    /**
     * Casts a vertical downwards line from the argument point and determines if there is an intersect. Useful for determining
     * if a point is within a boundary.
     * @param thePoint the other vector
     * @return whether if this edge is below the point
     */
    public boolean rayCast(final Vector2 thePoint) {
        return thePoint.getX() < myStart.getX() != thePoint.getX() < myEnd.getX()
                && thePoint.getY() < VMath.lerpY(myStart, myEnd, thePoint.getX());
    }

    /**
     * Gets the intersection point between this edge and another line segment.
     *
     * @see VMath#intersect(Vector2, Vector2, Vector2, Vector2)
     * @param theStart the start of the line segment
     * @param theEnd   the end of the line segment
     * @return the intersecting vector if it exists
     */
    public Vector2 getIntersect(final Vector2 theStart, final Vector2 theEnd) {
//        System.out.printf("lineY1: %f, lineY2: %f, VpY: %f, VpOY: %f\n", myStart.getY(), myEnd.getY(), theVp.getPosition().getY(), theVp.getOldPosition().getY());
        return VMath.intersect(myStart, myEnd, theStart, theEnd);
    }

    /**
     * Reflects a point over this edge.
     *
     * @see VMath#reflect(Vector2, Vector2, Vector2)
     * @param thePoint the point to reflect
     * @return the reflected point
     */
    public Vector2 reflectPoint(final Vector2 thePoint) {
        return VMath.reflect(myStart, myEnd, thePoint);
    }

    /**
     * Gets the start vector.
     * @return the start vector reference
     */
    public Vector2 getStart() {
        return myStart;
    }

    /**
     * Gets the end vector.
     * @return the end vector reference
     */
    public Vector2 getEnd() {
        return myEnd;
    }

    /**
     * Gets the edge as a vector (end - start).
     * @return the edge vector
     */
    public Vector2 getEdge() {
        return myEnd.subNew(myStart);
    }

    /**
     * Gets the perpendicular vector from the edge vector (end - start).
     * <br>
     * <b>Note:</b> the direction is counter-clockwise from the edge vector.
     * @return
     */
    public Vector2 getPerp() {
        return getEdge().perpNew();
    }

    /***
     * Gets the midpoint of this edge.
     * @return the midpoint
     */
    public Vector2 getMiddle() {
        return VMath.findMidpoint(myStart, myEnd);
    }
}
