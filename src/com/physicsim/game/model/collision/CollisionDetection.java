package com.physicsim.game.model.collision;

import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.model.rigidbody.RigidBodyEdge;
import com.physicsim.game.model.rigidbody.RigidCircle;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;

public final class CollisionDetection {
    private CollisionDetection() {}

    /**
     * Collision detection between a verlet object (particle) and a rigid body. Tests the verlet object against
     * every edge of the rigid body so the time complexity is θ(E). If there is a collision, returns the closest edge
     * to the particle.
     * @param theVO the verlet object
     * @param theRB the rigid body
     * @return a manifold containing the collision point, collision normal, and penetration vector. Null if no collision
     */
    public static Manifold detect(final VerletObject theVO, final RigidBody theRB) {
        Vector2 normal = null;
        RigidBodyEdge bestEdge = null;
        double bestProjection = Double.NEGATIVE_INFINITY;

        for (final RigidBodyEdge e : theRB.getEdges()) {
            // gets the normal vector of an edge
            final Vector2 edgeNormalOfRB = e.getPerp();

            // projects the support on to the normal to find the deepest penetrating vertex
            double projection = theVO.getPosition().subNew(e.getStart()).dotProduct(edgeNormalOfRB.normNew()); // normalizing :(

            if (projection > bestProjection) {
                bestProjection = projection;
                normal = edgeNormalOfRB;
                bestEdge = e;
            }
        }

        // if projection is greater than 0, the position of the VO is not contained within the body
        if (bestProjection <= 0 && bestEdge != null) {
            final Vector2 penVector = VMath.project(bestEdge.getStart(), bestEdge.getEnd(), theVO.getPosition());

            penVector.sub(theVO.getPosition());
            return new Manifold(theVO.getPosition(), normal, penVector);
        }

        return null;
    }

    /**
     * Collision detection between a Verlet object (particle) and a rigid circle. Tests if the particle collides
     * against the circumference of the circle, then reflects it over the tangent of the collision.
     * @param theVO the verlet object
     * @param theRC the rigid circle
     * @return a manifold containing the collision point, collision normal, and penetration vector. Null if no collision
     */
    public static Manifold detect(final VerletObject theVO, final RigidCircle theRC) {
        final double radiusSq = theRC.getRadius() * theRC.getRadius();
        final Vector2 toOrigin = theVO.getPosition().subNew(theRC.getCenterOfMass());

        if (toOrigin.dotProduct(toOrigin) > radiusSq) return null;

        final Vector2 penVector = toOrigin.normNew();
        penVector.mul(theRC.getRadius());
        penVector.add(theRC.getCenterOfMass());
        penVector.sub(theVO.getPosition());

        return new Manifold(theVO.getPosition(), toOrigin.mulNew(-1), penVector);
    }

    /**
     * Collision detection between 2 rigid bodies. Tests using an optimized SAT algorithm that uses support points.
     * Time complexity is θ(2(E * V)) where E is the number of edges on rigid body A, V is
     * the number of vertices on rigid body B.
     * @param theA the rigid body A
     * @param theB the rigid body B
     * @return a manifold containing the collision point, collision normal, and penetration vector. Null if no collision
     */
    public static Manifold detect(final RigidBody theA, final RigidBody theB) {
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
            final Vector2 projStart = VMath.project(edgeA.getStart(), edgeA.getEnd(), edgeB.getStart());
            final Vector2 projEnd = VMath.project(edgeA.getStart(), edgeA.getEnd(), edgeB.getEnd());

            if (projStart == null ^ projEnd == null) {
                // Parallelogram case
                final Vector2 projCenterAB = VMath.project(edgeB.getStart(), edgeB.getEnd(), theA.getCenterOfMass());
                final Vector2 projCenterBA = VMath.project(edgeA.getStart(), edgeA.getEnd(), theB.getCenterOfMass());

                if (projCenterAB == null) {
                    // center of mass is not hit
                    collisionPoint = projStart == null ? VMath.findMidpoint(projEnd, edgeA.getEnd())
                            : VMath.findMidpoint(projStart, edgeA.getStart());
                } else {
                    // center of mass is hit
                    collisionPoint = VMath.project(edgeA.getStart(), edgeA.getEnd(), theA.getCenterOfMass());
                }

                if (projCenterBA != null) {
                    // center of mass is hit
                    collisionPoint2 = VMath.project(edgeB.getStart(), edgeB.getEnd(), theB.getCenterOfMass());
                }
            } else {
                // Trapezoidal case
                collisionPoint = VMath.project(edgeA.getStart(), edgeA.getEnd(), theA.getCenterOfMass());

                final Vector2 projCenterBA = VMath.project(edgeA.getStart(), edgeA.getEnd(), theB.getCenterOfMass());
                if (projCenterBA != null) {
                    // center of mass is hit
                    collisionPoint2 = VMath.project(edgeB.getStart(), edgeB.getEnd(), theB.getCenterOfMass());
                }
            }

            collisionNormal = ba[1];
            final Vector2 proj = VMath.project(edgeB.getStart(), edgeB.getEnd(), collisionPoint);
            penVector = proj.subNew(collisionPoint);
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
                final Vector2 vel = theA.getLinearVelocity().mulNew(-1);
                final Vector2 penAB = projAB.subNew(ba[0]);
                final Vector2 penBA = ab[0].subNew(projBA);
                penVector = vel.dotProduct(penAB) > vel.dotProduct(penBA) ? penAB : penBA;
                collisionPoint = penVector == penAB ? ba[0] : ab[0];
                collisionNormal = penVector == penAB ? ba[1] : ab[1];
            }
        }

        if (collisionPoint2 == null) return new Manifold(collisionPoint, collisionNormal, penVector);

        return new Manifold(collisionPoint, collisionPoint2, collisionNormal, penVector);
    }

    public static Manifold detect(final RigidCircle theRC, final RigidBody theRB) {
        Vector2 collisionPoint, penVector, collisionNormal;
        RigidBodyEdge bestEdge = null;
        double bestProjection = Double.NEGATIVE_INFINITY;

        // Find the edge with the best (deepest) projection
        for (final RigidBodyEdge e : theRB.getEdges()) {
            final Vector2 edgeNormal = e.getPerp().normNew();

            double projection = theRC.getCenterOfMass().subNew(e.getStart()).dotProduct(edgeNormal); // normalizing :(

            if (projection > bestProjection) {
                bestProjection = projection;
                bestEdge = e;
            }
        }

        final double radiusSq = theRC.getRadius() * theRC.getRadius();
        Vector2 projOnEdge = VMath.project(bestEdge.getStart(), bestEdge.getEnd(), theRC.getCenterOfMass());
        double flip = 1;

        // Determine if the projection is on the edge or one of its endpoints
        if (projOnEdge == null) {
            projOnEdge = (theRC.getCenterOfMass().subNew(bestEdge.getStart()).dotProduct(bestEdge.getEdge()) < 0)
                    ? bestEdge.getStart()   // Circle-corner collision at start
                    : bestEdge.getEnd();    // Circle-corner collision at end
        } else if (theRC.getCenterOfMass().subNew(bestEdge.getStart()).crossProduct(bestEdge.getEdge()) < 0) {
            // circle-edge collision if not null
            flip = -1;
        }

        final Vector2 toProj = projOnEdge.subNew(theRC.getCenterOfMass());

        // Check radius
        if (flip > 0 && toProj.dotProduct(toProj) > radiusSq) return null;

        collisionPoint = new Vector2(projOnEdge);
        penVector = toProj.normNew();
        penVector.mul(theRC.getRadius() * flip);
        // Reverse the penetration vector to get the collision normal
        collisionNormal = new Vector2(-penVector.getX(), -penVector.getY());
        penVector = toProj.subNew(penVector);

        return new Manifold(collisionPoint, collisionNormal, penVector);
    }

    public static Manifold detect(final RigidCircle theA, final RigidCircle theB) {
        final Vector2 ab = theB.getCenterOfMass().subNew(theA.getCenterOfMass());
        final double radiusSum = theA.getRadius() + theB.getRadius();

        if (ab.dotProduct(ab) > radiusSum * radiusSum) return null;

        final Vector2 collisionNormal = new Vector2(-ab.getX(), -ab.getY());
        ab.norm();
        final Vector2 collisionPoint = ab.mulNew(theA.getRadius());
        collisionPoint.add(theA.getCenterOfMass());
        final Vector2 penVector = ab.mulNew(-theB.getRadius());
        penVector.add(theB.getCenterOfMass());
        penVector.sub(collisionPoint);


        return new Manifold(collisionPoint, collisionNormal, penVector);
    }
}
