package com.physicsim.game.model.collision;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.model.GameWorld;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.model.rigidbody.RigidBodyEdge;
import com.physicsim.game.model.rigidbody.RigidCircle;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for handling and managing all the collisions in the game world.
 */
public final class CollisionManager {
    /** The game world containing all the game objects. */
    private final GameWorld myWorld;
    /** Every collision at this instance. */
    private final List<Collision> myCollisions;

    /**
     * Constructs the manager.
     * @param theWorld the game world
     */
    public CollisionManager(final GameWorld theWorld) {
        myWorld = theWorld;
        myCollisions = new ArrayList<>();
    }

    /**
     * Tests a verlet object for collisions against dynamic and static objects.
     * Time complexity is O(S + D) where S is the number of static objects and D is the number of dynamic objects.
     * Can be improved using spatial partitioning.
     * @param theVO
     */
    public void testAndHandle(final VerletObject theVO) {
        for (final GameObject staticObject : myWorld.getStaticObjects()) {
            /*
             * Collision detection and resolution against a verlet object (particle). Tests the verlet object against
             * every edge of this rigid body so the time complexity is θ(E). If there is a collision, reflects the verlet
             * particle over the closest edge.
             */
            if (staticObject instanceof final RigidBody r) {
                RigidBodyEdge closestEdge = null;
                double shortestDistance = Integer.MAX_VALUE;

                for (final RigidBodyEdge e : r.getEdges()) {
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
                    theVO.getPosition().set(closestEdge.reflectPoint(theVO.getPosition()));
                    theVO.getOldPosition().set(closestEdge.reflectPoint(theVO.getOldPosition()));
                }
            }
            else if (staticObject instanceof final RigidCircle r) {
                /*
                 * Collision detection and resolution against a Verlet object (particle). Tests if the particle collides
                 * against the circumference of the circle, then reflects it over the tangent of the collision.
                 */
                if (theVO.getOldPosition().getDistance(r.getOrigin()) == r.getRadius()) continue;

                final Vector2[] intersections = VMath.intersect(theVO.getOldPosition(), theVO.getPosition(), r.getOrigin(), r.getRadius());
                if (intersections.length < 1) continue;

                // if more than 1 intersection, get the one closest to the old position
                final Vector2 intersect = intersections.length > 1
                        && intersections[1].getDistance(theVO.getOldPosition()) < intersections[0].getDistance(theVO.getOldPosition())
                        ? intersections[1] : intersections[0];
                final Vector2 tanVector = new Vector2();

                try {
                    final double m = VMath.slope(r.getOrigin(), intersect);
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

                theVO.getPosition().set(VMath.reflect(intersect, tanVector, theVO.getPosition()));
                theVO.getOldPosition().set(VMath.reflect(intersect, tanVector, theVO.getOldPosition()));
            }
        }
    }

    /**
     * Tests a rigid body for collisions against dynamic and static objects.
     * Time complexity is O(S + D) where S is the number of static objects and D is the number of dynamic objects.
     * Can be improved using spatial partitioning.
     * @param theRB
     */
    public void testAndHandle(final RigidBody theRB) {
        for (final GameObject staticObject : myWorld.getStaticObjects()) {
            if (staticObject instanceof final RigidBody r) {
                final Vector2[] a = VMath.findAxisOfPenetration(theRB, r);
                if (a.length == 0) continue;

                final Vector2[] b = VMath.findAxisOfPenetration(r, theRB);
                if (b.length == 0) continue;

//                System.out.println("Dynamic: pt:" + a[0] + ", norm: " + a[1] + ", index: " + a[2].getX());
//                System.out.println("Static: pt:" + b[0] + ", norm: " + b[1] + ", index: " + b[2].getX());

                // at this point, there is at least 1 point that is overlapping
                final int ia = a[2].intX();
                final int ib = b[2].intX();
                // projects the point to the edge
                final Vector2 projAB = VMath.project(r.getEdges()[ib].getStart(), r.getEdges()[ib].getEnd(), b[0]);
                final Vector2 projBA = VMath.project(theRB.getEdges()[ia].getStart(), theRB.getEdges()[ia].getEnd(), a[0]);

                if (projAB == null) {
                    assert projBA != null;
                    final Vector2 penVector = a[0].subNew(projBA);
                    myCollisions.add(new RigidBodyAndRigidBodyCollision(theRB, r, a[0], a[1], penVector));
                } else if (projBA == null) {
                    final Vector2 penVector = projAB.subNew(b[0]);
                    myCollisions.add(new RigidBodyAndRigidBodyCollision(theRB, r, b[0], b[1], penVector));
                } else {
                    final Vector2 vel = theRB.getLinearVelocity();
                    vel.mul(-1);
                    final Vector2 penAB = projAB.subNew(b[0]);
                    final Vector2 penBA = a[0].subNew(projBA);
                    if (vel.dotProduct(penAB) > vel.dotProduct(penBA)) {
                        myCollisions.add(new RigidBodyAndRigidBodyCollision(theRB, r, b[0], b[1], penAB));
                    } else {
                        myCollisions.add(new RigidBodyAndRigidBodyCollision(theRB, r, a[0], a[1], penBA));
                    }
                }
            }
        }
    }

    /**
     * Handles all the collisions.
     */
    public void update() {
        if (myCollisions.isEmpty()) return;

        for (final Collision c: myCollisions) {
            c.handleCollision();
        }
        myCollisions.clear();
    }
}
