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
     * @return the closest edge to the verlet object if there is a collision, null if no collision
     */
    public static RigidBodyEdge detect(final VerletObject theVO, final RigidBody theRB) {
        RigidBodyEdge closestEdge = null;
        double shortestDistance = Integer.MAX_VALUE;

        for (final RigidBodyEdge e : theRB.getEdges()) {
            Vector2 intersection = e.getIntersect(theVO.getPosition(), theVO.getOldPosition());
            if (intersection != null) {
                double dist = theVO.getOldPosition().getDistance(intersection);
                if (dist < shortestDistance) {
                    shortestDistance = dist;
                    closestEdge = e;
                }
            }
        }
        if (closestEdge != null
                // check co-linearity
                && closestEdge.getEdge().crossProduct(theVO.getOldPosition().subNew(closestEdge.getStart())) != 0) {
            return closestEdge;
        }

        return null;
    }

    /**
     * Collision detection between a Verlet object (particle) and a rigid circle. Tests if the particle collides
     * against the circumference of the circle, then reflects it over the tangent of the collision.
     * @param theVO the verlet object
     * @param theRC the rigid circle
     * @return a vector array containing the intersection point, and the circle tangential vector. Null if no collision
     */
    public static Vector2[] detect(final VerletObject theVO, final RigidCircle theRC) {
        if (theVO.getOldPosition().getDistance(theRC.getOrigin()) == theRC.getRadius()) return null;

        final Vector2[] intersections = VMath.intersect(theVO.getOldPosition(), theVO.getPosition(), theRC.getOrigin(), theRC.getRadius());
        if (intersections.length < 1) return null;

        // if more than 1 intersection, get the one closest to the old position
        final Vector2 intersect = intersections.length > 1
                && intersections[1].getDistance(theVO.getOldPosition()) < intersections[0].getDistance(theVO.getOldPosition())
                ? intersections[1] : intersections[0];
        final Vector2 tanVector = new Vector2();

        try {
            final double m = VMath.slope(theRC.getOrigin(), intersect);
            if (m == 0) {
                // horizontal slope (intersection point is directly left/right of center)
                tanVector.set(intersect.getX(), intersect.getY() + 1);
            } else {
                final double tangent = -1 / m;
                tanVector.set(intersect.getX() + 1, intersect.getY() + tangent);
            }
        } catch (final ArithmeticException e) {
            // vertical slope (intersection point is directly top/bot of center)
            tanVector.set(intersect.getX() + 1, intersect.getY());
        }

        return new Vector2[] {intersect, tanVector};
    }

    /**
     * Collision detection between 2 rigid bodies. Tests using an optimized SAT algorithm that uses support points.
     * Time complexity is θ(2(E * V)) where E is the number of edges on rigid body A, V is
     * the number of vertices on rigid body B.
     * @param theA the rigid body A
     * @param theB the rigid body B
     * @return a vector array containing the collision point, collision normal, and penetration vector. Null if no collision
     */
    public static Vector2[] detect(final RigidBody theA, final RigidBody theB) {
        final Vector2[] ab = VMath.findAxisOfPenetration(theA, theB);
        if (ab.length == 0) return null;

        final Vector2[] ba = VMath.findAxisOfPenetration(theB, theA);
        if (ba.length == 0) return null;

        final int ia = ab[2].intX();
        final int ib = ba[2].intX();
        Vector2 collisionPoint, collisionNormal, penVector;

//            System.out.println("Point: " + a[0] + ", Normal: " + a[1] +", Index: " + a[2].intX());
//            System.out.println("Point: " + b[0] + ", Normal: " + b[1] +", Index: " + b[2].intX());

        // Check for parallel edges using cross product
        final boolean edgesParallel = Math.abs(theA.getEdges()[ia].getEdge().crossProduct(theB.getEdges()[ib].getEdge())) < 0.00001;
        if (edgesParallel) {
            // edge to edge collision
            final Vector2 projStart = VMath.project(theA.getEdges()[ia].getStart(), theA.getEdges()[ia].getEnd(), theB.getEdges()[ib].getStart());
            final Vector2 projEnd = VMath.project(theA.getEdges()[ia].getStart(), theA.getEdges()[ia].getEnd(), theB.getEdges()[ib].getEnd());

            if (projStart == null ^ projEnd == null) {
                // Parallelogram case
                final Vector2 projCenter = VMath.project(theB.getEdges()[ib].getStart(), theB.getEdges()[ib].getEnd(), theA.getCenterOfMass());
                if (projCenter == null) {
                    // center of mass is not hit
                    collisionPoint = projStart == null ? VMath.findMidpoint(projEnd, theA.getEdges()[ia].getEnd())
                            : VMath.findMidpoint(projStart, theA.getEdges()[ia].getStart());
                } else {
                    // center of mass is hit
                    collisionPoint = VMath.project(theA.getEdges()[ia].getStart(), theA.getEdges()[ia].getEnd(), theA.getCenterOfMass());
                }
            } else {
                // Trapezoidal case
                collisionPoint = VMath.project(theA.getEdges()[ia].getStart(), theA.getEdges()[ia].getEnd(), theA.getCenterOfMass());
            }

            collisionNormal = ba[1];
            final Vector2 proj = VMath.project(theB.getEdges()[ib].getStart(), theB.getEdges()[ib].getEnd(), collisionPoint);
            penVector = proj.subNew(collisionPoint);
        } else {
            final Vector2 projAB = VMath.project(theB.getEdges()[ib].getStart(), theB.getEdges()[ib].getEnd(), ba[0]);
            final Vector2 projBA = VMath.project(theA.getEdges()[ia].getStart(), theA.getEdges()[ia].getEnd(), ab[0]);

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

        return new Vector2[] {collisionPoint, collisionNormal, penVector};
    }
}
