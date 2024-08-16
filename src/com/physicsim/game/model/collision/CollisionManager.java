package com.physicsim.game.model.collision;

import com.physicsim.game.controller.GameplayController;
import com.physicsim.game.model.GameObject;
import com.physicsim.game.model.GameWorld;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.model.rigidbody.Rigid2D;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.model.rigidbody.RigidCircle;
import com.physicsim.game.utility.Vector2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * A class for handling and managing all the collisions in the game world.
 */
public final class CollisionManager {
    /** The coefficient of restitution. ( 0 <= x <= 1)*/
    public static double COE_RES = 1;
    /** The game world containing all the game objects. */
    private final GameWorld myWorld;
    private final Map<String, BiConsumer<GameObject, GameObject>> myCollisionType;

    /**
     * Constructs the manager.
     * @param theWorld the game world
     */
    public CollisionManager(final GameWorld theWorld) {
        myWorld = theWorld;
        myCollisionType = new HashMap<>();

        myCollisionType.put("RigidBody-RigidBody", (theA, theB) -> handleRigidBodyAndRigidBody((RigidBody) theA, (RigidBody) theB));
        myCollisionType.put("RigidBody-RigidCircle", (theA, theB) -> handleRigidBodyAndRigidCircle((RigidBody) theA, (RigidCircle) theB));
        myCollisionType.put("RigidBody-VerletObject", (theA, theB) -> handleRigidBodyAndVerletObject((RigidBody) theA, (VerletObject) theB));
        
        myCollisionType.put("RigidCircle-RigidBody", (theA, theB) -> handleRigidCircleAndRigidBody((RigidCircle) theA, (RigidBody) theB));
        myCollisionType.put("RigidCircle-RigidCircle", (theA, theB) -> handleRigidCircleAndRigidCircle((RigidCircle) theA, (RigidCircle) theB));
        myCollisionType.put("RigidCircle-VerletObject", (theA, theB) -> handleRigidCircleAndVerletObject((RigidCircle) theA, (VerletObject) theB));
        
        myCollisionType.put("VerletObject-RigidBody", (theA, theB) -> handleVerletObjectAndRigidBody((VerletObject) theA, (RigidBody) theB));
        myCollisionType.put("VerletObject-RigidCircle", (theA, theB) -> handleVerletObjectAndRigidCircle((VerletObject) theA, (RigidCircle) theB));
        myCollisionType.put("VerletObject-VerletObject", (theA, theB) -> handleVerletObjectAndVerletObject((VerletObject) theA, (VerletObject) theB));
    }

    private String getKeyPairing(final GameObject theA, final GameObject theB) {
        return theA.getName() + "-" + theB.getName();
    }

    /**
     * Tests a verlet object for collisions against dynamic and static objects.
     * Time complexity is O(S + D) where S is the number of static objects and D is the number of dynamic objects.
     * Can be improved using spatial partitioning.
     * @param theA the verlet object to test
     */
    public void testAndHandle(final GameObject theA, final int theIndex) {
        for (final GameObject staticObject : myWorld.getStaticObjects()) {
            myCollisionType.get(getKeyPairing(theA, staticObject)).accept(theA, staticObject);
        }

        for (int i = theIndex + 1; i < myWorld.getDynamicObjects().size(); i++) {
            final GameObject dynObject = myWorld.getDynamicObjects().get(i);
            myCollisionType.get(getKeyPairing(theA, dynObject)).accept(theA, dynObject);
        }
    }

    // **********========== All Handlers ==========********** \\

    private void handleVerletObjectAndVerletObject(final VerletObject theA, final VerletObject theB) {

    }

    private void handleVerletObjectAndRigidCircle(final VerletObject theA, final RigidCircle theB) {
        final Manifold manifold = CollisionDetection.detect(theA, theB);
        if (manifold != null) {
            handle1DCollision(theA, theB, manifold);
        }
    }

    private void handleVerletObjectAndRigidBody(final VerletObject theA, final RigidBody theB) {
        final Manifold manifold = CollisionDetection.detect(theA, theB);
        if (manifold != null) {
            handle1DCollision(theA, theB, manifold);
        }
    }

    private void handleRigidCircleAndVerletObject(final RigidCircle theA, final VerletObject theB) {
        final Manifold manifold = CollisionDetection.detect(theB, theA);
        if (manifold != null) {
            manifold.getPenetration().mul(-1);
            manifold.getNormal().mul(-1);
            handle1DCollision(theA, theB, manifold);
        }
    }

    private void handleRigidCircleAndRigidCircle(final RigidCircle theA, final RigidCircle theB) {
        final Manifold manifold = CollisionDetection.detect(theA, theB);
        if (manifold != null) {
            handle2DCollision(theA, theB, manifold);
        }
    }

    private void handleRigidCircleAndRigidBody(final RigidCircle theA, final RigidBody theB) {
        final Manifold manifold = CollisionDetection.detect(theA, theB);
        if (manifold != null) {
            handle2DCollision(theA, theB, manifold);
        }
    }

    private void handleRigidBodyAndVerletObject(final RigidBody theA, final VerletObject theB) {
        final Manifold manifold = CollisionDetection.detect(theB, theA);
        if (manifold != null) {
            handle1DCollision(theA, theB, manifold);
        }
    }

    private void handleRigidBodyAndRigidCircle(final RigidBody theA, final RigidCircle theB) {
        final Manifold manifold = CollisionDetection.detect(theB, theA);
        if (manifold != null) {
            manifold.getPenetration().mul(-1);
            manifold.getNormal().mul(-1);
            handle2DCollision(theA, theB, manifold);
        }
    }

    private void handleRigidBodyAndRigidBody(final RigidBody theA, final RigidBody theB) {
        final Manifold manifold = CollisionDetection.detect(theA, theB);
        if (manifold != null) {
            handle2DCollision(theA, theB, manifold);
        }
    }

    // **********========== All Responses ==========********** \\
    /**
     * <br>
     * @see <a href="https://chrishecker.com/images/e/e7/Gdmphys3.pdf">Formula Used. </a>
     */
    private void handle2DCollision(final Rigid2D theA, final Rigid2D theB, final Manifold theManifold) {
        final double sumInvMassA = 1 / theA.getMass() + (theB.hasDynamics() ? (1 / theB.getMass()) : 0);
        final double normalSquared = theManifold.getNormal().dotProduct(theManifold.getNormal());
        final Vector2 relVelA = theA.getLinearVelocity().addNew(theA.getLinearAngularVelocity(theManifold.getPoint()));
        final Vector2 relVelB = theB.getLinearVelocity().addNew(theB.getLinearAngularVelocity(theManifold.getPoint()));
        final double relNorm = relVelB.subNew(relVelA).dotProduct(theManifold.getNormal());

        final Vector2 rA = theManifold.getPoint().subNew(theA.getCenterOfMass());
        final Vector2 rB = theManifold.getPoint().subNew(theB.getCenterOfMass());
        final double angA = theManifold.getNormal().crossProduct(rA);
        final double angB = theManifold.getNormal().crossProduct(rB);

        final double denom = normalSquared * sumInvMassA + (angA * angA / theA.getMoi()) + (theB.hasDynamics() ? (angB * angB / theB.getMoi()) : 0);
        final double impulse = -(1 + COE_RES) * relNorm / denom;

        theA.translate(theManifold.getPenetration());
        theA.applyImpulse(-impulse, theManifold.getNormal(), rA);
        if (theB.hasDynamics()) theB.applyImpulse(impulse, theManifold.getNormal(), rB);
    }

    private void handle1DCollision(final VerletObject theA, final Rigid2D theB, final Manifold theManifold) {
        double impulse = get1DImpulse(theA, theB, theManifold);
        final Vector2 rB = theManifold.getPoint().subNew(theB.getCenterOfMass());

        theA.translate(theManifold.getPenetration());
        theA.applyImpulse(-impulse, theManifold.getNormal());
        if (theB.hasDynamics()) theB.applyImpulse(impulse, theManifold.getNormal(), rB);
    }

    private void handle1DCollision(final Rigid2D theA, final VerletObject theB, final Manifold theManifold) {
        double impulse = get1DImpulse(theB, theA, theManifold);
        final Vector2 rA = theManifold.getPoint().subNew(theA.getCenterOfMass());

        theA.translate(theManifold.getPenetration());
        theA.applyImpulse(impulse, theManifold.getNormal(), rA);
        if (theB.hasDynamics()) theB.applyImpulse(-impulse, theManifold.getNormal());
    }

    private double get1DImpulse(final VerletObject theA, final Rigid2D theB, final Manifold theManifold) {
        final double sumInvMassA = 1 / theA.getMass() + (theB.hasDynamics() ? (1 / theB.getMass()) : 0);
        final double normalSquared = theManifold.getNormal().dotProduct(theManifold.getNormal());
        final Vector2 relVelA = theA.getVelocity();
        final Vector2 relVelB = theB.getLinearVelocity().addNew(theB.getLinearAngularVelocity(theManifold.getPoint()));
        final double relNorm = relVelB.subNew(relVelA).dotProduct(theManifold.getNormal());

        final double denom = normalSquared * sumInvMassA;
        return -(1 + COE_RES) * relNorm / denom;
    }
}
