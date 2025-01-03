package com.physicsim.game.model.collision.response;

import com.physicsim.game.model.collision.Manifold;
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
        myManifold.getNormal().norm();
    }

    @Override
    public void handleResponse() {
        final double sumInvMassA = 1 / myA.getSplitMass() + (myB.hasDynamics() ? (1 / myB.getSplitMass()) : 0);
        final double normalSquared = myManifold.getNormal().dotProduct(myManifold.getNormal());
        final Vector2 relVelA = myA.getLinearVelocity().addNew(myA.getLinearAngularVelocity(myManifold.getPoint()));
        final Vector2 relVelB = myB.getLinearVelocity().addNew(myB.getLinearAngularVelocity(myManifold.getPoint()));
        final double relNorm = relVelB.subNew(relVelA).dotProduct(myManifold.getNormal());

        final Vector2 rA = myManifold.getPoint().subNew(myA.getCenterOfMass());
        Vector2 rB = myManifold.getPoint().subNew(myB.getCenterOfMass());
        final double angA = myManifold.getNormal().crossProduct(rA);
        final double angB = myManifold.getNormal().crossProduct(rB);

//        System.out.println("relnorm: " + relNorm);
//        System.out.println("AngA: " + angA + ", AngB: " + angB);

        final double denom = normalSquared * sumInvMassA + (angA * angA / myA.getMoi()) + (myB.hasDynamics() ? (angB * angB / myB.getMoi()) : 0);
        final double impulse = -(1 + COE_RES) * relNorm / denom;


        myA.applyImpulse(-impulse, myManifold.getNormal(), rA);
        if (myB.hasDynamics()) {
            if (myManifold.getPoint2() != null) {
                rB = myManifold.getPoint2().subNew(myB.getCenterOfMass());
            }

            myB.applyImpulse(impulse, myManifold.getNormal(), rB);

            final Vector2 half = myManifold.getPenetration().divNew(2);
            myA.translate(half);
            half.mul(-1);
            myB.translate(half);
        } else {
            myA.translate(myManifold.getPenetration());
        }
    }
}
