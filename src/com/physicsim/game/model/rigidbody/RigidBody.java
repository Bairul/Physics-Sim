package com.physicsim.game.model.rigidbody;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;

/**
 * Abstract class for a rigid body physics game object. Must be a convex polygon.
 */
public abstract class RigidBody extends GameObject {

    /** The vertices of this rigid body. */
    private final Vector2[] myVertices;
    /** The edges of this rigid body. */
    private final RigidBodyEdge[] myEdges;
    private final Vector2 myCenter;

    /**
     * Creates a rigid body given a bunch of vertices.
     * @param theVertices the number of new vertices as vectors.
     * @throws IllegalArgumentException if it is not a convex 2d polygon that have 3 or more vertices
     */
    public RigidBody(final Vector2... theVertices) {
        // valid vertices
        if (theVertices.length < 2) throw new IllegalArgumentException("A rigid body must have 3 or more vertices");

        myVertices = theVertices;
        myCenter = new Vector2();
        myEdges = new RigidBodyEdge[theVertices.length];
        for (int i = 0; i < myEdges.length; i++) {
            myEdges[i] = new RigidBodyEdge(myVertices[i], myVertices[(i + 1) % myEdges.length]);
            myCenter.add(myVertices[i]);
        }

        // is convex
        int convexity = (int) Math.signum(myEdges[0].getEdge().crossProduct(myEdges[1].getEdge()));
        for (int i = 1; i < myEdges.length - 1; i++) {
            if ((int) Math.signum(myEdges[i].getEdge().crossProduct(myEdges[i + 1].getEdge())) * convexity < 0) {
                throw new IllegalArgumentException("A rigid body must be convex");
            }
        }
        myCenter.div(myVertices.length);
    }

    /**
     * Determines whether a point is contained in this rigid body. Uses ray casting algorithm
     * with a time complexity of θ(E) where E is the number of Edges in this rigid body.
     * @param thePoint the point
     * @return whether it is contained
     */
    public boolean overlaps(final Vector2 thePoint) {
        boolean count = false;
        for (final RigidBodyEdge e : myEdges) {
            if (e.rayCast(thePoint)) {
                count = !count;
            }
        }

        return count;
    }

    /**
     * Collision detection and resolution against a verlet object (particle).
     * Tests the verlet object against every edge of this rigid body so the
     * time complexity is θ(E). If there is a collision, reflects the verlet
     * particle over the closest edge.
     * @param theVO the verlet object
     */
    public void collideAgainst(final VerletObject theVO) {
        RigidBodyEdge closestEdge = null;
        double shortestDistance = Integer.MAX_VALUE;

        for (final RigidBodyEdge e : myEdges) {
            Vector2 intersection = e.getIntersect(theVO.getPosition(), theVO.getOldPosition());
            if (intersection != null) {
                double dist = theVO.getOldPosition().getDistance(intersection);
                if (dist < shortestDistance) {
                    shortestDistance = dist;
                    closestEdge = e;
                }
            }
        }

        // if there is a closest edge and the old position is not a point on the edge (solves a tunneling bug when the
        // point collides perfectly with the edge)
        if (closestEdge != null
                && closestEdge.getEdge().crossProduct(theVO.getOldPosition().subNew(closestEdge.getStart())) != 0) {
            theVO.getPosition().set(closestEdge.reflectPoint(theVO.getPosition()));
            theVO.getOldPosition().set(closestEdge.reflectPoint(theVO.getOldPosition()));
        }
    }

    public void collideAgainst(final RigidBody theRB) {

    }

    public void rotate(final double theDegrees) {
        for (final Vector2 v : myVertices) {
            VMath.rotate(v, myCenter, Math.toRadians(theDegrees));
        }
    }

    public void translate(final Vector2 theTranslation) {
        for (final Vector2 v : myVertices) {
            v.add(theTranslation);
        }
        myCenter.add(theTranslation);
    }

    public RigidBodyEdge[] getEdges() {
        return myEdges;
    }

    public Vector2[] getVertices() {
        return myVertices;
    }

    public Vector2 getCenter() {
        return myCenter;
    }
}
