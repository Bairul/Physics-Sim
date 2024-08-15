package com.physicsim.game.model.collision;

import com.physicsim.game.controller.GameplayController;
import com.physicsim.game.model.GameObject;
import com.physicsim.game.model.GameWorld;
import com.physicsim.game.model.collision.responses.CollisionResponse;
import com.physicsim.game.model.collision.responses.RigidBodyAndRigidBodyCollision;
import com.physicsim.game.model.collision.responses.VerletObjectAndRigidBodyCollision;
import com.physicsim.game.model.collision.responses.VerletObjectAndRigidCircleCollision;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.model.rigidbody.RigidBodyEdge;
import com.physicsim.game.model.rigidbody.RigidCircle;
import com.physicsim.game.utility.VMath;
import com.physicsim.game.utility.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for handling and managing all the collisions in the game world.
 */
public final class CollisionManager {
    /** The coefficient of restitution. ( 0 <= x <= 1)*/
    public static double COE_RES = 1;
    /** The game world containing all the game objects. */
    private final GameWorld myWorld;
    /** Every collision at this instance. */
    private final List<CollisionResponse> myCollisions;

    /**
     * Constructs the manager.
     * @param theWorld the game world
     */
    public CollisionManager(final GameWorld theWorld) {
        myWorld = theWorld;
        myCollisions = new ArrayList<>();
    }

    /**
     * Tests a verlet object for collisions against dynamic and static objects.
     * Time complexity is O(S + D) where S is the number of static objects and D is the number of dynamic objects.
     * Can be improved using spatial partitioning.
     * @param theVO the verlet object to test
     */
    public void testAndHandle(final VerletObject theVO) {
        for (final GameObject staticObject : myWorld.getStaticObjects()) {

            if (staticObject instanceof final RigidBody r) {
                final Manifold manifold = CollisionDetection.detect(theVO, r);
                if (manifold != null) {
                    myCollisions.add(new VerletObjectAndRigidBodyCollision(theVO, r, manifold));
//                    theVO.getPosition().set(closestEdge.reflectPoint(theVO.getPosition()));
//                    theVO.getOldPosition().set(closestEdge.reflectPoint(theVO.getOldPosition()));
                }
            }
            else if (staticObject instanceof final RigidCircle r) {
                final Manifold manifold = CollisionDetection.detect(theVO, r);
                if (manifold != null) {
                    myCollisions.add(new VerletObjectAndRigidCircleCollision(theVO, r, manifold));
//                    theVO.getPosition().set(VMath.reflect(manifold[0], manifold[1], theVO.getPosition()));
//                    theVO.getOldPosition().set(VMath.reflect(manifold[0], manifold[1], theVO.getOldPosition()));
                }
            }
        }
    }

    /**
     * Tests a rigid body for collisions against dynamic and static objects.
     * Time complexity is O(S + D) where S is the number of static objects and D is the number of dynamic objects.
     * Can be improved using spatial partitioning.
     * @param theRB the rigid body to test
     */
    public void testAndHandle(final RigidBody theRB, final int theIndex) {
        for (final GameObject staticObject : myWorld.getStaticObjects()) {
            if (staticObject instanceof final RigidBody r) {
                final Manifold manifold = CollisionDetection.detect(theRB, r);
                if (manifold != null) {
                    myCollisions.add(new RigidBodyAndRigidBodyCollision(theRB, r, manifold));
                }
            }
        }

        for (int i = theIndex + 1; i < myWorld.getDynamicObjects().size(); i++) {
            final GameObject dynObject = myWorld.getDynamicObjects().get(i);

            if (dynObject instanceof final RigidBody r) {
                final Manifold manifold = CollisionDetection.detect(theRB, r);
                if (manifold != null) {
                    myCollisions.add(new RigidBodyAndRigidBodyCollision(theRB, r, manifold));
                }
            }
        }
    }


    /**
     * Handles all the collisions.
     */
    public void update() {
        if (myCollisions.isEmpty()) return;

        for (final CollisionResponse c: myCollisions) {
            c.handleCollision();
        }
        myCollisions.clear();
    }
}
