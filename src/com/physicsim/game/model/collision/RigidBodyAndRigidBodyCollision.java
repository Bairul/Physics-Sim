package com.physicsim.game.model.collision;

import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.utility.Vector2;

public final class RigidBodyAndRigidBodyCollision extends Collision {
    /** The coefficient of restitution. */
    public static double COE_RES = 1;
    /** The rigid body A. */
    private final RigidBody myA;
    /** The rigid body B. */
    private final RigidBody myB;
    /** The point of collision. */
    private final Vector2 myCollisionPoint;
    /** The normal vector. */
    private final Vector2 myCollisionNormal;
    /** The minimum translation vector. */
    private final Vector2 myPenetrationVector;

    /**
     * Constructs a collision manifold.
     * @param theA                 the rigid body A
     * @param theB                 the rigid body B
     * @param theCollisionPoint    the collision vector
     * @param theCollisionNormal   the normal vector of the collision
     * @param thePenetrationVector the penetration vector to translate the body to not overlap
     */
    public RigidBodyAndRigidBodyCollision(final RigidBody theA, final RigidBody theB, final Vector2 theCollisionPoint,
                                          final Vector2 theCollisionNormal, final Vector2 thePenetrationVector) {
        myA = theA;
        myB = theB;
        myCollisionPoint = theCollisionPoint;
        myCollisionNormal = theCollisionNormal;
        myPenetrationVector = thePenetrationVector;
    }

    /**
     * {@inheritDoc}
     * <br>
     * @see <a href="https://chrishecker.com/images/e/e7/Gdmphys3.pdf">Formula Used. </a>
     */
    @Override
    public void handleCollision() {
        final double sumInvMassA = 1 / myA.getMass() + (myB.hasPhysics() ? (1 / myB.getMass()) : 0);
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

        if (myB.hasPhysics()) {
//            double sumMass = myA.getMass() + myB.getMass();
//            myA.translate(myPenetrationVector.mulNew(myB.getMass() / sumMass));
//            myB.translate(myPenetrationVector.mulNew(-myA.getMass() / sumMass));
//            myA.applyImpulse(-impulse, myCollisionNormal, rA);
//            myB.applyImpulse(impulse, myCollisionNormal, rB);
        } else {
            myA.translate(myPenetrationVector);
            myA.applyImpulse(-impulse, myCollisionNormal, rA);
        }
    }
}
