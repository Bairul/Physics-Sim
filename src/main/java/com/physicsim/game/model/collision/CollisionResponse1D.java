package com.physicsim.game.model.collision;

import com.physicsim.game.model.collision.response.CollisionResponse;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.model.rigidbody.Rigid2D;
import com.physicsim.game.utility.Vector2;

import static com.physicsim.game.model.collision.CollisionManager.COE_RES;

public class CollisionResponse1D extends CollisionResponse {
    private final VerletObject myVO;
    private final Rigid2D myRB;
    private final Manifold myManifold;
    private final boolean isReversed;

    public CollisionResponse1D(final VerletObject theA, final Rigid2D theB, final Manifold theManifold) {
        myVO = theA;
        myRB = theB;
        myManifold = theManifold;
        isReversed = false;
    }

    public CollisionResponse1D(final Rigid2D theA, final VerletObject theB, final Manifold theManifold) {
        myVO = theB;
        myRB = theA;
        myManifold = theManifold;
        isReversed = true;
    }

    @Override
    public void handleResponse() {
        double impulse = get1DImpulse();
        final Vector2 r = myManifold.getPoint().subNew(myRB.getCenterOfMass());

//        if (isReversed) {
//            myRB.translate(myManifold.getPenetration());
//            myRB.applyImpulse(impulse, myManifold.getNormal(), r);
//            if (myVO.hasDynamics()) myVO.applyImpulse(-impulse, myManifold.getNormal());
//        } else {
//            myVO.translate(myManifold.getPenetration());
//            myVO.applyImpulse(-impulse, myManifold.getNormal());
//            if (myRB.hasDynamics()) myRB.applyImpulse(impulse, myManifold.getNormal(), r);
//        }
    }

    private double get1DImpulse() {
        final double sumInvMassA = 1 / myVO.getMass() + (myRB.hasDynamics() ? (1 / myRB.getSplitMass()) : 0);
        final double normalSquared = myManifold.getNormal().dotProduct(myManifold.getNormal());
        final Vector2 relVelA = myVO.getVelocity();
        final Vector2 relVelB = myRB.getLinearVelocity().addNew(myRB.getLinearAngularVelocity(myManifold.getPoint()));
        final double relNorm = relVelB.subNew(relVelA).dotProduct(myManifold.getNormal());

        final double denom = normalSquared * sumInvMassA;
        return -(1 + COE_RES) * relNorm / denom;
    }
}
