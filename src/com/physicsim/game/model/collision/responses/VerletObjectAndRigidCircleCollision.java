package com.physicsim.game.model.collision.responses;

import com.physicsim.game.model.collision.Manifold;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.model.rigidbody.RigidCircle;

public class VerletObjectAndRigidCircleCollision extends CollisionResponse {

    private final VerletObject myVO;
    private final RigidCircle myRC;
    private final Manifold myManifold;

    public VerletObjectAndRigidCircleCollision(final VerletObject theVO, final RigidCircle theRC, final Manifold theManifold) {
        myVO = theVO;
        myRC = theRC;
        myManifold = theManifold;
    }
    @Override
    public void handleCollision() {

    }
}
