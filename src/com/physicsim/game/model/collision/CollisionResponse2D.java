package com.physicsim.game.model.collision;

import com.physicsim.game.model.collision.response.CollisionResponse;
import com.physicsim.game.model.rigidbody.Rigid2D;
import com.physicsim.game.utility.Vector2;

import static com.physicsim.game.model.collision.CollisionManager.COE_RES;

public class CollisionResponse2D extends CollisionResponse {
    private final Rigid2D myA;
    private final Rigid2D myB;
    private final Manifold myManifold;

    public CollisionResponse2D(final Rigid2D theA, final Rigid2D theB, final Manifold theManifold) {
        myA = theA;
        myB = theB;
        myManifold = theManifold;
    }

    @Override
    public void handleResponse() {
        final double sumInvMassA = 1 / myA.getSplitMass() + (myB.hasDynamics() ? (1 / myB.getSplitMass()) : 0);
        final double normalSquared = myManifold.getNormal().dotProduct(myManifold.getNormal());
        final Vector2 relVelA = myA.getLinearVelocity().addNew(myA.getLinearAngularVelocity(myManifold.getPoint()));
        final Vector2 relVelB = myB.getLinearVelocity().addNew(myB.getLinearAngularVelocity(myManifold.getPoint()));
        final double relNorm = relVelB.subNew(relVelA).dotProduct(myManifold.getNormal());

        final Vector2 rA = myManifold.getPoint().subNew(myA.getCenterOfMass());
        final Vector2 rB = myManifold.getPoint().subNew(myB.getCenterOfMass());
        final double angA = myManifold.getNormal().crossProduct(rA);
        final double angB = myManifold.getNormal().crossProduct(rB);

        final double denom = normalSquared * sumInvMassA + (angA * angA / myA.getMoi()) + (myB.hasDynamics() ? (angB * angB / myB.getMoi()) : 0);
        final double impulse = -(1 + COE_RES) * relNorm / denom;

        myA.translate(myManifold.getPenetration());
        myA.applyImpulse(-impulse, myManifold.getNormal(), rA);
        if (myB.hasDynamics()) myB.applyImpulse(impulse, myManifold.getNormal(), rB);
    }
}
