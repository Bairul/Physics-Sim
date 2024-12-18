package com.physicsim.game.model.collision;

import com.physicsim.game.utility.Vector2;

public final class Manifold {
    private final Vector2 myCollisionPoint;
    private final Vector2 myCollisionPoint2;
    private final Vector2 myCollisionNormal;
    private final Vector2 myPenetrationVector;
    public Manifold(final Vector2 theCollisionPoint, final Vector2 theCollisionNormal, final Vector2 thePenetrationVector) {
        this(theCollisionPoint, null, theCollisionNormal, thePenetrationVector);
    }

    public Manifold(final Vector2 theCollisionPoint, final Vector2 theCollisionPoint2, final Vector2 theCollisionNormal, final Vector2 thePenetrationVector) {
        myCollisionPoint = theCollisionPoint;
        myCollisionNormal = theCollisionNormal;
        myPenetrationVector = thePenetrationVector;
        myCollisionPoint2 = theCollisionPoint2;
    }

    public Vector2 getNormal() {
        return myCollisionNormal;
    }

    public Vector2 getPoint() {
        return myCollisionPoint;
    }

    public Vector2 getPoint2() {
        return myCollisionPoint2;
    }

    public Vector2 getPenetration() {
        return myPenetrationVector;
    }
}
