package com.physicsim.game.model.collision.responses;

import com.physicsim.game.model.collision.Manifold;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.utility.Vector2;

import static com.physicsim.game.model.collision.CollisionManager.COE_RES;

public final class RigidBodyAndRigidBodyCollision extends CollisionResponse {
    /** The rigid body A. */
    private final RigidBody myA;
    /** The rigid body B. */
    private final RigidBody myB;
    /** The point of collision. */
    private final Manifold myManifold;

    /**
     * Constructor for a collision pair.
     * @param theA        the rigid body A
     * @param theB        the rigid body B
     * @param theManifold the collision manifold
     */
    public RigidBodyAndRigidBodyCollision(final RigidBody theA, final RigidBody theB, final Manifold theManifold) {
        myA = theA;
        myB = theB;
        myManifold = theManifold;
    }

    /**
     * {@inheritDoc}
     * <br>
     * @see <a href="https://chrishecker.com/images/e/e7/Gdmphys3.pdf">Formula Used. </a>
     */
    @Override
    public void handleCollision() {
        final double sumInvMassA = 1 / myA.getMass() + (myB.hasPhysics() ? (1 / myB.getMass()) : 0);
        final double normalSquared = myManifold.getNormal().dotProduct(myManifold.getNormal());
        final Vector2 relVelA = myA.getLinearVelocity().addNew(myA.getLinearAngularVelocity(myManifold.getPoint()));
        final Vector2 relVelB = myB.getLinearVelocity().addNew(myB.getLinearAngularVelocity(myManifold.getPoint()));
        final double relNorm = relVelB.subNew(relVelA).dotProduct(myManifold.getNormal());

        final Vector2 rA = myManifold.getPoint().subNew(myA.getCenterOfMass());
        final Vector2 rB = myManifold.getPoint().subNew(myB.getCenterOfMass());
        final double angA = myManifold.getNormal().crossProduct(rA);
        final double angB = myManifold.getNormal().crossProduct(rB);

        final double denom = normalSquared * sumInvMassA + (angA * angA / myA.getMoi()) + (myB.hasPhysics() ? (angB * angB / myB.getMoi()) : 0);
        final double impulse = -(1 + COE_RES) * relNorm / denom;

        myA.translate(myManifold.getPenetration());
        myA.applyImpulse(-impulse, myManifold.getNormal(), rA);
        if (myB.hasPhysics()) myB.applyImpulse(impulse, myManifold.getNormal(), rB);
    }
}
