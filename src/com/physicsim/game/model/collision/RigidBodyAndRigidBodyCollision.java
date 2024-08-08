package com.physicsim.game.model.collision;

import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.utility.Vector2;

public class RigidBodyAndRigidBodyCollision extends Collision {
    /** The coefficient of restitution. */
    public static double COE_RES = 1;
    private final RigidBody myA;
    private final RigidBody myB;
    private final Vector2 myCollisionPoint;
    private final Vector2 myCollisionNormal;
    public RigidBodyAndRigidBodyCollision(final RigidBody theA, final RigidBody theB, final Vector2 theCollisionPoint, final Vector2 theCollisionNormal) {
        myA = theA;
        myB = theB;
        myCollisionPoint = theCollisionPoint;
        myCollisionNormal = theCollisionNormal;
    }
    @Override
    public void handleCollision() {
        final double sumInvMassA = 1 / myA.getMass() + (myB.hasPhysics() ? 1 / myB.getMass() : 0);
        final double normalSquared = myCollisionNormal.dotProduct(myCollisionNormal);
        final Vector2 relVelA = myA.getLinearVelocity().addNew(myA.getLinearAngularVelocity(myCollisionPoint));
        final Vector2 relVelB = myB.getLinearVelocity().addNew(myB.getLinearAngularVelocity(myCollisionPoint));
        final double relNorm = relVelB.subNew(relVelA).dotProduct(myCollisionNormal);

        final Vector2 rA = myCollisionPoint.subNew(myA.getCenterOfMass());
        final Vector2 rB = myCollisionPoint.subNew(myB.getCenterOfMass());
        final double angA = myCollisionNormal.crossProduct(rA);
        final double angB = myCollisionNormal.crossProduct(rB);

        final double denom = normalSquared * sumInvMassA + (angA * angA / myA.getMoi()) + (myB.hasPhysics() ? (angB * angB / myB.getMoi()) : 0);
        final double impulse = -(1 + COE_RES) * relNorm / denom;

        if (myA.hasPhysics()) myA.applyImpulse(-impulse, myCollisionNormal, rA);
        if (myB.hasPhysics()) myB.applyImpulse(impulse, myCollisionNormal, rB);
    }
}
