package com.physicsim.game.model.collision.response;

import com.physicsim.game.model.collision.Manifold;
import com.physicsim.game.model.rigidbody.Rigid2D;

public class RigidBodyCollisionResponse extends CollisionResponse {

    private final Rigid2D myA;
    private final Rigid2D myB;
    private final Manifold myManifold;

    public RigidBodyCollisionResponse(final Rigid2D theA, final Rigid2D theB, final Manifold theManifold) {
        myA = theA;
        myB = theB;
        myManifold = theManifold;
        myManifold.getNormal().norm();
    }

    @Override
    public void handleResponse() {

    }
}
