package com.physicsim.game.model.collision.responses;

import com.physicsim.game.model.collision.Manifold;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.utility.Vector2;

import static com.physicsim.game.model.collision.CollisionManager.COE_RES;

public final class VerletObjectAndRigidBodyCollision extends CollisionResponse {
    /** The verlet object. */
    private final VerletObject myVO;
    /** The rigid body. */
    private final RigidBody myRB;
    /** The point of collision. */
    private final Manifold myManifold;
    public VerletObjectAndRigidBodyCollision(final VerletObject theVO, final RigidBody theRB, final Manifold theManifold) {
        myVO = theVO;
        myRB = theRB;
        myManifold = theManifold;
    }
    @Override
    public void handleCollision() {
        final double sumInvMassA = 1 / myVO.getMass() + (myRB.hasPhysics() ? (1 / myRB.getMass()) : 0);
        final double normalSquared = myManifold.getNormal().dotProduct(myManifold.getNormal());
        final Vector2 relVelA = myVO.getVelocity();
        final Vector2 relVelB = myRB.getLinearVelocity().addNew(myRB.getLinearAngularVelocity(myManifold.getPoint()));
        final double relNorm = relVelB.subNew(relVelA).dotProduct(myManifold.getNormal());

        final Vector2 rB = myManifold.getPoint().subNew(myRB.getCenterOfMass());

        final double denom = normalSquared * sumInvMassA;
        final double impulse = -(1 + COE_RES) * relNorm / denom;

        myVO.translate(myManifold.getPenetration());
        myVO.applyImpulse(-impulse, myManifold.getNormal());
        if (myRB.hasPhysics()) myRB.applyImpulse(impulse, myManifold.getNormal(), rB);
    }
}
