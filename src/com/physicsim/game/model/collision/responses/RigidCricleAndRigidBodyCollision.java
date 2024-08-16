package com.physicsim.game.model.collision.responses;

import com.physicsim.game.controller.GameplayController;
import com.physicsim.game.model.collision.Manifold;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.model.rigidbody.RigidCircle;
import com.physicsim.game.utility.Vector2;

import static com.physicsim.game.model.collision.CollisionManager.COE_RES;

public class RigidCricleAndRigidBodyCollision extends CollisionResponse {
    /** The rigid body A. */
    private final RigidCircle myRC;
    /** The rigid body B. */
    private final RigidBody myRB;
    /** The point of collision. */
    private final Manifold myManifold;

    /**
     * Constructor for a collision pair.
     * @param theRC        the rigid body A
     * @param theRB        the rigid body B
     * @param theManifold the collision manifold
     */
    public RigidCricleAndRigidBodyCollision(final RigidCircle theRC, final RigidBody theRB, final Manifold theManifold) {
        myRC = theRC;
        myRB = theRB;
        myManifold = theManifold;
    }

    /**
     * {@inheritDoc}
     * <br>
     * @see <a href="https://chrishecker.com/images/e/e7/Gdmphys3.pdf">Formula Used. </a>
     */
    @Override
    public void handleCollision() {
        final double sumInvMassA = 1 / myRC.getMass() + (myRB.hasPhysics() ? (1 / myRB.getMass()) : 0);
        final double normalSquared = myManifold.getNormal().dotProduct(myManifold.getNormal());
        final Vector2 relVelA = myRC.getLinearVelocity().addNew(myRC.getLinearAngularVelocity(myManifold.getPoint()));
        final Vector2 relVelB = myRB.getLinearVelocity().addNew(myRB.getLinearAngularVelocity(myManifold.getPoint()));
        final double relNorm = relVelB.subNew(relVelA).dotProduct(myManifold.getNormal());

        final Vector2 rA = myManifold.getPoint().subNew(myRC.getCenterOfMass());
        final Vector2 rB = myManifold.getPoint().subNew(myRB.getCenterOfMass());
        final double angA = myManifold.getNormal().crossProduct(rA);
        final double angB = myManifold.getNormal().crossProduct(rB);

        final double denom = normalSquared * sumInvMassA + (angA * angA / myRC.getMoi()) + (myRB.hasPhysics() ? (angB * angB / myRB.getMoi()) : 0);
        final double impulse = -(1 + COE_RES) * relNorm / denom;

        myRC.translate(myManifold.getPenetration());
        myRC.applyImpulse(-impulse, myManifold.getNormal(), rA);
        if (myRB.hasPhysics()) myRB.applyImpulse(impulse, myManifold.getNormal(), rB);
    }
}
