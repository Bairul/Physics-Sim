package com.survival.game.utility;

import com.survival.game.model.GameObject;
import com.survival.game.model.MovableObject;

import java.util.List;

public final class CheckCollide {
    private static final Vector2 myCacheVector = new Vector2();
    private CheckCollide() {}

    private static void handleCollision(final MovableObject theA, final MovableObject theB, final Vector2 theCache) {
        // normal vector
        theCache.set(theB.getPosition().getX() - theA.getPosition().getX(), theB.getPosition().getY() - theA.getPosition().getY());
        // unit normal vector
        theCache.normalize();
        // unit tangent vector
        myCacheVector.set(theCache.getY() * -1, theCache.getX());

        // projection of velocity vectors on unit normal and unit tangent vectors
        float v1n = theCache.dotProduct(theA.getVelocity());
        float v1t = myCacheVector.dotProduct(theA.getVelocity());
        float v2n = theCache.dotProduct(theB.getVelocity());
        float v2t = myCacheVector.dotProduct(theB.getVelocity());

        // the new normal velocities
        float newV1n = oneDimensionalNormal(theA.getMass(), theB.getMass(), v1n, v2n);
        float newV2n = oneDimensionalNormal(theB.getMass(), theA.getMass(), v2n, v1n);

        // set the new normal velocities
        theA.getVelocity().set(theCache);
        theB.getVelocity().set(theCache);
        theA.getVelocity().mul(newV1n);
        theB.getVelocity().mul(newV2n);

        // new tangent velocities
        theCache.set(myCacheVector);
        theCache.mul(v1t);
        myCacheVector.mul(v2t);

        // new velocities by adding their respective normal and tangent components
        theA.getVelocity().add(theCache);
        theB.getVelocity().add(myCacheVector);
    }

    private static float oneDimensionalNormal(final float theMassA, final float theMassB, final float theV1N, final float theV2N) {
        return (theMassA - theMassB) * theV1N + 2 * theMassB * theV2N / (theMassA + theMassB);
    }

    public static void checkForCollisions(final List<GameObject> gameObjects, final Vector2 theCache) {

        // naive approach for testing O(n^2)
        for (int i = 0; i < gameObjects.size() - 1; i++) {
            MovableObject a = (MovableObject) gameObjects.get(i);
            for (int j = i + 1; j < gameObjects.size(); j++) {
                MovableObject b = (MovableObject) gameObjects.get(j);
                myCacheVector.set(b.getPosition());
                // gets the distance vector of a and b
                myCacheVector.sub(a.getPosition());

                if (myCacheVector.getMagnitude() <= a.getRadius() + b.getRadius()) {
                    // collided
                    System.out.println("collision!");
                    handleCollision(a, b, theCache);
                }
            }
        }
    }
}
