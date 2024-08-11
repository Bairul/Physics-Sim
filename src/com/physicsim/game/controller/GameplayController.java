package com.physicsim.game.controller;

import com.physicsim.game.controller.input.ClickType;
import com.physicsim.game.controller.input.KeyType;
import com.physicsim.game.model.*;
import com.physicsim.game.model.collision.CollisionManager;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.model.rigidbody.RegularPolygon;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.controller.input.InputController;
import com.physicsim.game.view.GameScreen;

/**
 * This class is used for handling and controlling objects in the game world. This includes updating
 * and rendering them. In other words, this class connects the model and the view for the gameplay.
 *
 * @author Bairu Li
 */
public class GameplayController {
    /** The user inputs. */
    private final InputController myInputs;
    /** World that holds entities and other stuff. */
    private final GameWorld myGameWorld;
    /** A reusable vector to save some memory. */
    private final CollisionManager myCollisionManager;
    private final Vector2 myCache;
    private boolean debugMode;

    /**
     * Constructs the gameplay controller.
     * @param theInput the input controller
     */
    public GameplayController(final InputController theInput) {
        myInputs = theInput;
        myGameWorld = new GameWorld();
        myCollisionManager = new CollisionManager(myGameWorld);
        myCache = new Vector2();
        // boundary will be at the center of the game screen
        GameWorld.SCREEN_BOUNDARY.set(GameScreen.getWidth() >> 1, GameScreen.getHeight() >> 1);
        GameWorld.GRAVITY.set(0, 0.1);
        debugMode = false;

        // static polygon
        myCache.set(-200, 200);
        RegularPolygon b = new RegularPolygon(myCache, 3, 400, 1);
        b.setAngularPosition(Math.toRadians(10));
        myGameWorld.addStaticObject(b);
    }

    /**
     * Updates the state of the game.
     *
     */
    public void update() {
        if (myInputs.getKeyboard().isKeyDown(KeyType.D)) {
            debugMode = !debugMode;
        }

        if (myInputs.getMouse().isButtonDown(ClickType.LeftClick)) {
            System.out.println(myInputs.getMousePos());
            RegularPolygon b = new RegularPolygon(myInputs.getMousePos(), 3, 100, 4);
            b.setPhysics(true);
//            b.setLinearVelocity(new Vector2(0, 3));
            b.setAngularPosition(Math.toRadians(-55));
            myGameWorld.addDynamicObject(b);
        }

        if (myInputs.getKeyboard().isKeyHeld(KeyType.Space)) {
            myGameWorld.clearDynamicObjects();
        }

        if (debugMode) {
            if (!myInputs.getKeyboard().isKeyDown(KeyType.S))
                return;
        }

        // O(D * S) --> every dynamic object with every static object
        for (final GameObject dynObject : myGameWorld.getDynamicObjects())  {
            if (dynObject instanceof final VerletObject p) {
                myCache.set(GameWorld.GRAVITY);
                myCache.mul(p.getMass());
                p.applyForce(myCache);
                p.bounceOffBoundary(GameWorld.SCREEN_BOUNDARY);
//                p.update();
                myCollisionManager.testAndHandle(p);
//                continue;
            }

            if (dynObject instanceof final RigidBody r) {
                myCache.set(GameWorld.GRAVITY);
                myCache.mul(r.getMass());
                r.applyLinearForce(myCache);
                if (myCollisionManager.testAndHandle(r)) {
//                    myCollisionManager.update();
//                    r.update();
//                    debugMode = true;
//                    continue;
//                    myGameWorld.clearStaticObjects();
                }
            }
        }
        myCollisionManager.update();
        myGameWorld.getDynamicObjects().forEach(GameObject::update);
    }

    /**
     * Gets the list of game objects.
     *
     * @return list of game objects
     */
    public GameWorld getGameWorld() {
        return myGameWorld;
    }

}
