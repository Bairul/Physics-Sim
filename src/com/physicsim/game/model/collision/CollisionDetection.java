package com.physicsim.game.model.collision;

import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.model.rigidbody.RigidBodyEdge;
import com.physicsim.game.model.rigidbody.RigidCircle;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;

import java.util.Objects;

public final class CollisionDetection {
    private CollisionDetection() {}

    /**
     * Collision detection between 2 rigid bodies (A onto B). Tests using an optimized SAT algorithm that uses support points.
     * Time complexity is θ(2(E * V)) where E is the number of edges on rigid body A, and V is
     * the number of vertices on rigid body B.
     * @param theA the rigid body A
     * @param theB the rigid body B
     * @return a manifold containing the collision point, collision normal, and penetration vector. Null if no collision
     */
    public static Manifold[] detect(final RigidBody theA, final RigidBody theB) {
        final Vector2[] ab = VMath.findAxisOfPenetration(theA, theB);
        if (ab.length == 0) return null;

        final Vector2[] ba = VMath.findAxisOfPenetration(theB, theA);
        if (ba.length == 0) return null;

        final RigidBodyEdge edgeA = theA.getEdges()[ab[2].intX()];
        final RigidBodyEdge edgeB = theB.getEdges()[ba[2].intX()];
        Vector2 collisionPoint, collisionPoint2 = null, collisionNormal, penVector;

//            System.out.println("Point: " + a[0] + ", Normal: " + a[1] +", Index: " + a[2].intX());
//            System.out.println("Point: " + b[0] + ", Normal: " + b[1] +", Index: " + b[2].intX());

        // Check for parallel edges using cross product
        final boolean edgesParallel = Math.abs(edgeA.getEdge().crossProduct(edgeB.getEdge())) < 0.00001;

        if (edgesParallel) {
            // edge to edge collision
            boolean isTrapezoidal = false;
            final Vector2 projStart = VMath.project(edgeA.getStart(), edgeA.getEnd(), edgeB.getStart());
            final Vector2 projEnd = VMath.project(edgeA.getStart(), edgeA.getEnd(), edgeB.getEnd());

            if (projStart == null && projEnd == null) {
                // trapezoidal case, A is smaller
                collisionPoint = edgeA.getStart();
                collisionPoint2 = edgeA.getEnd();
                isTrapezoidal = true;
            } else if (projStart != null && projEnd != null) {
                // trapezoidal case, A is bigger
                collisionPoint = edgeB.getStart();
                collisionPoint2 = edgeB.getEnd();
                isTrapezoidal = true;
            } else if (projEnd == null) {
                // Parallelogram case, start side
                collisionPoint = edgeA.getStart();
                collisionPoint2 = edgeB.getStart();
            } else {
                // Parallelogram case, end side
                collisionPoint = edgeA.getEnd();
                collisionPoint2 = edgeB.getEnd();
            }

            collisionNormal = ba[1];
            final Vector2 proj = VMath.project(edgeB.getStart(), edgeB.getEnd(), collisionPoint);
            penVector = Objects.requireNonNull(proj).subNew(collisionPoint);

            if (!isTrapezoidal) collisionPoint = proj;
        } else {
            final Vector2 projAB = VMath.project(edgeB.getStart(), edgeB.getEnd(), ba[0]);
            final Vector2 projBA = VMath.project(edgeA.getStart(), edgeA.getEnd(), ab[0]);

            // corner to edge collision
            if (projAB == null) {
                // body B is a corner
                collisionPoint = ab[0];
                collisionNormal = ab[1];
                penVector = collisionPoint.subNew(projBA);
            } else if (projBA == null) {
                // body A is a corner
                collisionPoint = ba[0];
                collisionNormal = ba[1];
                penVector = projAB.subNew(collisionPoint);
            } else {
                // corner to corner collision
                final Vector2 vel = theA.getVelocity().toVector2().mulNew(-1);
                final Vector2 penAB = projAB.subNew(ba[0]);
                final Vector2 penBA = ab[0].subNew(projBA);
                penVector = vel.dotProduct(penAB) > vel.dotProduct(penBA) ? penAB : penBA;
                collisionPoint = penVector == penAB ? ba[0] : ab[0];
                collisionNormal = penVector == penAB ? ba[1] : ab[1];
            }
        }

        if (collisionPoint2 == null) return new Manifold[] { new Manifold(collisionPoint, collisionNormal, penVector) };

        return new Manifold[] { new Manifold(collisionPoint, collisionNormal, penVector), new Manifold(collisionPoint2, collisionNormal, penVector) };
    }

//    public static Manifold detect(final RigidCircle theRC, final RigidBody theRB) {
//        Vector2 collisionPoint, penVector, collisionNormal;
//        RigidBodyEdge bestEdge = null;
//        double bestProjection = Double.NEGATIVE_INFINITY;
//
//        // Find the edge with the best (deepest) projection
//        for (final RigidBodyEdge e : theRB.getEdges()) {
//            final Vector2 edgeNormal = e.getPerp().normNew();
//
//            double projection = theRC.getCenterOfMass().subNew(e.getStart()).dotProduct(edgeNormal); // normalizing :(
//
//            if (projection > bestProjection) {
//                bestProjection = projection;
//                bestEdge = e;
//            }
//        }
//
//        final double radiusSq = theRC.getRadius() * theRC.getRadius();
//        Vector2 projOnEdge = VMath.project(bestEdge.getStart(), bestEdge.getEnd(), theRC.getCenterOfMass());
//        double flip = 1;
//
//        // Determine if the projection is on the edge or one of its endpoints
//        if (projOnEdge == null) {
//            projOnEdge = (theRC.getCenterOfMass().subNew(bestEdge.getStart()).dotProduct(bestEdge.getEdge()) < 0)
//                    ? bestEdge.getStart()   // Circle-corner collision at start
//                    : bestEdge.getEnd();    // Circle-corner collision at end
//        } else if (theRC.getCenterOfMass().subNew(bestEdge.getStart()).crossProduct(bestEdge.getEdge()) < 0) {
//            // circle-edge collision if not null
//            flip = -1;
//        }
//
//        final Vector2 toProj = projOnEdge.subNew(theRC.getCenterOfMass());
//
//        // Check radius
//        if (flip > 0 && toProj.dotProduct(toProj) > radiusSq) return null;
//
//        collisionPoint = new Vector2(projOnEdge);
//        penVector = toProj.normNew();
//        penVector.mul(theRC.getRadius() * flip);
//        // Reverse the penetration vector to get the collision normal
//        collisionNormal = new Vector2(-penVector.getX(), -penVector.getY());
//        penVector = toProj.subNew(penVector);
//
//        return new Manifold(collisionPoint, collisionNormal, penVector);
//    }

//    public static Manifold detect(final RigidCircle theA, final RigidCircle theB) {
//        final Vector2 ab = theB.getCenterOfMass().subNew(theA.getCenterOfMass());
//        final double radiusSum = theA.getRadius() + theB.getRadius();
//
//        if (ab.dotProduct(ab) > radiusSum * radiusSum) return null;
//
//        final Vector2 collisionNormal = new Vector2(-ab.getX(), -ab.getY());
//        ab.norm();
//        final Vector2 collisionPoint = ab.mulNew(theA.getRadius());
//        collisionPoint.add(theA.getCenterOfMass());
//        final Vector2 penVector = ab.mulNew(-theB.getRadius());
//        penVector.add(theB.getCenterOfMass());
//        penVector.sub(collisionPoint);
//
//
//        return new Manifold(collisionPoint, collisionNormal, penVector);
//    }
}
