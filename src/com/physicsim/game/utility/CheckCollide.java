package com.physicsim.game.utility;

public final class CheckCollide {
    /*
    private static final Vector2 myCacheVector = new Vector2();
    private CheckCollide() {}

    private static void handleCollision(final MovableObject theA, final MovableObject theB, final Vector2 theNormal) {
        float overlappingLength = theA.getRadius() + theB.getRadius() - theNormal.getMagnitude();
        // unit normal vector
        theNormal.unit();
        myCacheVector.set(theNormal);

        // resolve overlapping first by repelling them by their normal vector
        myCacheVector.mul(overlappingLength / 2);
        theB.getPosition().add(myCacheVector);
        myCacheVector.mul(-1);
        theA.getPosition().add(myCacheVector);

        // unit tangent vector
        myCacheVector.set(theNormal.getY() * -1, theNormal.getX());

        // projection of velocity vectors on unit normal and unit tangent vectors
        float v1n = theNormal.dotProduct(theA.getVelocity());
        float v1t = myCacheVector.dotProduct(theA.getVelocity());
        float v2n = theNormal.dotProduct(theB.getVelocity());
        float v2t = myCacheVector.dotProduct(theB.getVelocity());

        // get the new normal velocities after collision
        float newV1n = oneDimensionalNormal(theA.getMass(), theB.getMass(), v1n, v2n);
        float newV2n = oneDimensionalNormal(theB.getMass(), theA.getMass(), v2n, v1n);

        // set the new normal velocities to the moving objects
        theA.getVelocity().set(theNormal);
        theB.getVelocity().set(theNormal);
        theA.getVelocity().mul(newV1n);
        theB.getVelocity().mul(newV2n);

        // get new tangent velocities after collision
        theNormal.set(myCacheVector);
        theNormal.mul(v1t);
        myCacheVector.mul(v2t);

        // set new velocities by adding their respective normal and tangent components
        theA.getVelocity().add(theNormal);
        theB.getVelocity().add(myCacheVector);
    }

    private static float oneDimensionalNormal(final float theMassA, final float theMassB, final float theV1N, final float theV2N) {
        return ((theMassA - theMassB) * theV1N + 2 * theMassB * theV2N) / (theMassA + theMassB);
    }

    public static void checkForCollisions(final List<GameObject> gameObjects, final Vector2 theCache) {

        // naive approach for testing O(n^2)
        for (int i = 0; i < gameObjects.size() - 1; i++) {
            MovableObject a = (MovableObject) gameObjects.get(i);
            for (int j = i + 1; j < gameObjects.size(); j++) {
                MovableObject b = (MovableObject) gameObjects.get(j);
                theCache.set(b.getPosition());
                // gets the distance vector of a and b (normal vector)
                theCache.sub(a.getPosition());

                if (theCache.getMagnitude() <= a.getRadius() + b.getRadius()) {
                    // collision
                    handleCollision(a, b, theCache);
                }
            }
        }
    }



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
public static Manifold detect(final RigidCircle circle, final RigidBody polygon) {
        Vector2 collisionPoint, penetrationVector, collisionNormal;
        RigidBodyEdge bestEdge = null;
        double maxProjection = Double.NEGATIVE_INFINITY;

        // Find the edge with the best (deepest) projection
        for (final RigidBodyEdge edge : polygon.getEdges()) {
            Vector2 edgeNormal = edge.getPerp().normNew();
            double projection = circle.getCenterOfMass().subNew(edge.getStart()).dotProduct(edgeNormal);

            if (projection > maxProjection) {
                maxProjection = projection;
                bestEdge = edge;
            }
        }

        final double radiusSquared = circle.getRadius() * circle.getRadius();
        Vector2 closestPointOnEdge = VMath.project(bestEdge.getStart(), bestEdge.getEnd(), circle.getCenterOfMass());
        double flip = 1;

        // Determine if the projection is on the edge or one of its endpoints
        if (closestPointOnEdge == null) {
            closestPointOnEdge = (circle.getCenterOfMass().subNew(bestEdge.getStart()).dotProduct(bestEdge.getEdge()) < 0)
                    ? bestEdge.getStart()  // Circle-corner collision at start
                    : bestEdge.getEnd();    // Circle-corner collision at end
        } else if (circle.getCenterOfMass().subNew(bestEdge.getStart()).crossProduct(bestEdge.getEdge()) < 0) {
            flip = -1; // Determine the correct collision side
        }

        Vector2 toClosestPoint = closestPointOnEdge.subNew(circle.getCenterOfMass());

        // Check if the circle is outside the edge's influence
        if (flip > 0 && toClosestPoint.dotProduct(toClosestPoint) >= radiusSquared) {
            return null;
        }

        // Calculate penetration and collision information
        penetrationVector = toClosestPoint.normNew().mul(circle.getRadius() * flip);
        collisionPoint = circle.getCenterOfMass().addNew(penetrationVector);
        collisionNormal = penetrationVector.negate(); // Reverse the penetration vector to get the collision normal
        penetrationVector = toClosestPoint.subNew(penetrationVector);

        return new Manifold(collisionPoint, collisionNormal, penetrationVector);
    }
     */
}
