package com.physicsim.game.model.collision;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.model.GameWorld;
import com.physicsim.game.model.collision.response.CollisionResponse;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.model.rigidbody.RigidCircle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * A class for handling and managing all the collisions in the game world.
 */
public final class CollisionManager {
    /** The coefficient of restitution. ( 0 <= x <= 1)*/
    public static double COE_RES = 0.9;
    /** The game world containing all the game objects. */
    private final GameWorld myWorld;
    private final Map<String, BiConsumer<GameObject, GameObject>> myCollisionType;
    private final List<CollisionResponse> myCollisionResponses;

    /**
     * Constructs the manager.
     * @param theWorld the game world
     */
    public CollisionManager(final GameWorld theWorld) {
        myWorld = theWorld;
        myCollisionType = new HashMap<>();
        myCollisionResponses = new ArrayList<>();

        myCollisionType.put("RigidBody-RigidBody", (theA, theB) -> detectRigidBodyAndRigidBody((RigidBody) theA, (RigidBody) theB));
        myCollisionType.put("RigidBody-RigidCircle", (theA, theB) -> detectRigidBodyAndRigidCircle((RigidBody) theA, (RigidCircle) theB));
        myCollisionType.put("RigidBody-VerletObject", (theA, theB) -> detectRigidBodyAndVerletObject((RigidBody) theA, (VerletObject) theB));
        
        myCollisionType.put("RigidCircle-RigidBody", (theA, theB) -> detectRigidCircleAndRigidBody((RigidCircle) theA, (RigidBody) theB));
        myCollisionType.put("RigidCircle-RigidCircle", (theA, theB) -> detectRigidCircleAndRigidCircle((RigidCircle) theA, (RigidCircle) theB));
        myCollisionType.put("RigidCircle-VerletObject", (theA, theB) -> detectRigidCircleAndVerletObject((RigidCircle) theA, (VerletObject) theB));
        
        myCollisionType.put("VerletObject-RigidBody", (theA, theB) -> detectVerletObjectAndRigidBody((VerletObject) theA, (RigidBody) theB));
        myCollisionType.put("VerletObject-RigidCircle", (theA, theB) -> detectVerletObjectAndRigidCircle((VerletObject) theA, (RigidCircle) theB));
        myCollisionType.put("VerletObject-VerletObject", (theA, theB) -> detectVerletObjectAndVerletObject((VerletObject) theA, (VerletObject) theB));
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
    public void detectCollisions(final GameObject theA, final int theIndex) {
        for (final GameObject staticObject : myWorld.getStaticObjects()) {
            myCollisionType.get(getKeyPairing(theA, staticObject)).accept(theA, staticObject);
        }

        for (int i = theIndex + 1; i < myWorld.getDynamicObjects().size(); i++) {
            final GameObject dynObject = myWorld.getDynamicObjects().get(i);
            myCollisionType.get(getKeyPairing(theA, dynObject)).accept(theA, dynObject);
        }
    }

    public void handleCollisions() {
        if (myCollisionResponses.isEmpty()) return;

        myCollisionResponses.forEach(CollisionResponse::handleResponse);
        myCollisionResponses.clear();
    }

    // **********========== All Handlers ==========********** \\

    private void detectVerletObjectAndVerletObject(final VerletObject theA, final VerletObject theB) {

    }

    private void detectVerletObjectAndRigidCircle(final VerletObject theA, final RigidCircle theB) {
        final Manifold manifold = CollisionDetection.detect(theA, theB);
        if (manifold != null) {
            theB.incCollision();
            myCollisionResponses.add(new CollisionResponse1D(theA, theB, manifold));
        }
    }

    private void detectVerletObjectAndRigidBody(final VerletObject theA, final RigidBody theB) {
        final Manifold manifold = CollisionDetection.detect(theA, theB);
        if (manifold != null) {
            theB.incCollision();
            myCollisionResponses.add(new CollisionResponse1D(theA, theB, manifold));
        }
    }

    private void detectRigidCircleAndVerletObject(final RigidCircle theA, final VerletObject theB) {
        final Manifold manifold = CollisionDetection.detect(theB, theA);
        if (manifold != null) {
            manifold.getPenetration().mul(-1);
            manifold.getNormal().mul(-1);
            theA.incCollision();
            myCollisionResponses.add(new CollisionResponse1D(theA, theB, manifold));
        }
    }

    private void detectRigidCircleAndRigidCircle(final RigidCircle theA, final RigidCircle theB) {
        final Manifold manifold = CollisionDetection.detect(theA, theB);
        if (manifold != null) {
            theA.incCollision();
            theB.incCollision();
            myCollisionResponses.add(new CollisionResponse2D(theA, theB, manifold));
        }
    }

    private void detectRigidCircleAndRigidBody(final RigidCircle theA, final RigidBody theB) {
        final Manifold manifold = CollisionDetection.detect(theA, theB);
        if (manifold != null) {
            theA.incCollision();
            theB.incCollision();
            myCollisionResponses.add(new CollisionResponse2D(theA, theB, manifold));
        }
    }

    private void detectRigidBodyAndVerletObject(final RigidBody theA, final VerletObject theB) {
        final Manifold manifold = CollisionDetection.detect(theB, theA);
        if (manifold != null) {
            manifold.getPenetration().mul(-1);
            manifold.getNormal().mul(-1);
            theA.incCollision();
            myCollisionResponses.add(new CollisionResponse1D(theA, theB, manifold));
        }
    }

    private void detectRigidBodyAndRigidCircle(final RigidBody theA, final RigidCircle theB) {
        final Manifold manifold = CollisionDetection.detect(theB, theA);
        if (manifold != null) {
            manifold.getPenetration().mul(-1);
            manifold.getNormal().mul(-1);
            theA.incCollision();
            theB.incCollision();
            myCollisionResponses.add(new CollisionResponse2D(theA, theB, manifold));
        }
    }

    private void detectRigidBodyAndRigidBody(final RigidBody theA, final RigidBody theB) {
        final Manifold manifold = CollisionDetection.detect(theA, theB);
        if (manifold != null) {
            theA.incCollision();
            theB.incCollision();
            myCollisionResponses.add(new CollisionResponse2D(theA, theB, manifold));
        }
    }
}
