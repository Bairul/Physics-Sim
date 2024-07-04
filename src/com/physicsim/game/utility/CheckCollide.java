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

     */
}
