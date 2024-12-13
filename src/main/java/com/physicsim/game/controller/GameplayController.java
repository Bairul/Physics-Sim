package com.physicsim.game.controller;

import com.physicsim.game.controller.input.ClickType;
import com.physicsim.game.controller.input.KeyType;
import com.physicsim.game.model.*;
import com.physicsim.game.model.collision.CollisionManager;
import com.physicsim.game.model.rigidbody.*;
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
    public static boolean debugMode;

    /**
     * Constructs the gameplay controller.
     * @param theInput the input controller
     */
    public GameplayController(final InputController theInput) {
        myInputs = theInput;
        myGameWorld = new GameWorld();
        myCollisionManager = new CollisionManager(myGameWorld);
        final Vector2 myCache = new Vector2();
        // boundary will be at the center of the game screen
        GameWorld.SCREEN_BOUNDARY.set(GameScreen.getWidth() >> 1, GameScreen.getHeight() >> 1);
        GameWorld.GRAVITY.set(0, 0.1);
        debugMode = false;

        // walls
        myCache.set(-GameWorld.SCREEN_BOUNDARY.getX(), GameWorld.SCREEN_BOUNDARY.getY());
        myGameWorld.addStaticObject(new Box(myCache, GameScreen.getWidth(), 50, 1));
        myCache.set(-GameWorld.SCREEN_BOUNDARY.getX() - 50, -GameWorld.SCREEN_BOUNDARY.getY());
        myGameWorld.addStaticObject(new Box(myCache, 50, GameScreen.getHeight(), 1));
        myCache.set(GameWorld.SCREEN_BOUNDARY.getX(), -GameWorld.SCREEN_BOUNDARY.getY());
        myGameWorld.addStaticObject(new Box(myCache, 50, GameScreen.getHeight(), 1));

        myCache.set(-GameWorld.SCREEN_BOUNDARY.getX(), -GameWorld.SCREEN_BOUNDARY.getY() - 50);
        myGameWorld.addStaticObject(new Box(myCache, GameScreen.getWidth(), 50, 1));

        myCache.set(0, 0);
        Rigid2D c = new RigidCircle(myCache, 50, 1);
        c.setDynamics(true);
        myGameWorld.addDynamicObject(c);

        myCache.set(10, -100);
        Rigid2D c2 = new RigidCircle(myCache, 50, 1);
        c2.setDynamics(true);
        myGameWorld.addDynamicObject(c2);
    }

    /**
     * Updates the state of the game.
     *
     */
    public void update() {
//        if (myInputs.getKeyboard().isKeyDown(KeyType.D)) {
//            debugMode = !debugMode;
//        }
//
//        // create object when mouse is pressed
//        if (myInputs.getMouse().isButtonDown(ClickType.LeftClick)) {
//            int l = (int) (Math.random() * 5) + 2;
//            Rigid2D c;
//            if (l <= 2) {
//                c = new RigidCircle(myInputs.getMousePos(), 50, 1);
//            } else {
//                c = new RegularPolygon(myInputs.getMousePos(), l, 50, 1);
//            }
//            c.setDynamics(true);
//            myGameWorld.addDynamicObject(c);
//        }
//
//        if (myInputs.getKeyboard().isKeyHeld(KeyType.Space)) {
//            myGameWorld.clearDynamicObjects();
//        }
//
//        if (debugMode) {
//            if (!myInputs.getKeyboard().isKeyDown(KeyType.S))
//                return;
//        }

        // O(D * (S + D)) --> every dynamic object with every static and dynamic object
        int index = 0;
        for (final GameObject dynObject : myGameWorld.getDynamicObjects())  {
            if (dynObject instanceof final Rigid2D p) {
                p.applyGravity();
                myCollisionManager.detectCollisions(p, index);
            }
            index++;
        }

        myCollisionManager.handleCollisions();

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
