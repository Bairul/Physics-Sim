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
    private final Vector2 myCache;
    public static boolean debugMode;

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

        // flat 2 static
//        myCache.set(-100.5, 100);
        myCache.set(-140, 100);
        RegularPolygon b = new RegularPolygon(myCache, 4, 200, 1);
        b.rotate(Math.toRadians(45));
        myGameWorld.addStaticObject(b);
//        myCache.set(100.5, 100);
        myCache.set(140, 100);
        RegularPolygon b2 = new RegularPolygon(myCache, 4, 200, 1);
        b2.rotate(Math.toRadians(45));
        myGameWorld.addStaticObject(b2);

        // flat 1 static
//        myCache.set(0, 100);
//        RegularPolygon b = new RegularPolygon(myCache, 4, 200, 1);
//        b.rotate(Math.toRadians(45));
//        myGameWorld.addStaticObject(b);

        // diagonal 2 static
//        myCache.set(-150, 100);
//        RegularPolygon b = new RegularPolygon(myCache, 4, 200, 1000);
//        b.setDynamics(true);
//        myGameWorld.addStaticObject(b);
//        myCache.set(150, 100);
//        RegularPolygon b2 = new RegularPolygon(myCache, 4, 200, 1000);
//        b2.setDynamics(true);
//        myGameWorld.addStaticObject(b2);

        // flat 2 dynamic
//        myCache.set(-101, 100);
//        RegularPolygon b = new RegularPolygon(myCache, 4, 200, 100000);
//        b.setDynamics(true);
//        b.rotate(Math.toRadians(45));
//        myGameWorld.addDynamicObject(b);
//        myCache.set(101, 100);
//        RegularPolygon b2 = new RegularPolygon(myCache, 4, 200, 100000);
//        b2.setDynamics(true);
//        b2.rotate(Math.toRadians(45));
//        myGameWorld.addDynamicObject(b2);

        // flat 1 dynamic
//        myCache.set(0, 100);
//        RegularPolygon b = new RegularPolygon(myCache, 4, 200, 1000);
//        b.rotate(Math.toRadians(45));
//        b.setDynamics(true);
//        myGameWorld.addDynamicObject(b);

        myCache.set(0, -150);
        RegularPolygon b3 = new RegularPolygon(myCache, 4, 100, 50);
        b3.setDynamics(true);
        b3.rotate(Math.toRadians(45));
        myCache.set(0, 1);
        b3.setLinearVelocity(myCache);
        myGameWorld.addDynamicObject(b3);

        // walls
        myCache.set(-GameWorld.SCREEN_BOUNDARY.getX(), GameWorld.SCREEN_BOUNDARY.getY());
        myGameWorld.addStaticObject(new Box(myCache, GameScreen.getWidth(), 50, 1));
        myCache.set(-GameWorld.SCREEN_BOUNDARY.getX() - 50, -GameWorld.SCREEN_BOUNDARY.getY());
        myGameWorld.addStaticObject(new Box(myCache, 50, GameScreen.getHeight(), 1));
        myCache.set(GameWorld.SCREEN_BOUNDARY.getX(), -GameWorld.SCREEN_BOUNDARY.getY());
        myGameWorld.addStaticObject(new Box(myCache, 50, GameScreen.getHeight(), 1));

        myCache.set(-GameWorld.SCREEN_BOUNDARY.getX(), -GameWorld.SCREEN_BOUNDARY.getY() - 50);
        myGameWorld.addStaticObject(new Box(myCache, GameScreen.getWidth(), 50, 1));
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
//            RegularPolygon b = new RegularPolygon(myInputs.getMousePos(), 4, 100, 100);
//            b.setDynamics(true);
//            b.rotate(Math.toRadians(45));
//            myGameWorld.addDynamicObject(b);
            RigidCircle c = new RigidCircle(myInputs.getMousePos(), 50, 100);
            c.setDynamics(true);
            myGameWorld.addDynamicObject(c);
        }

        if (myInputs.getKeyboard().isKeyHeld(KeyType.Space)) {
            myGameWorld.clearDynamicObjects();
        }

        if (debugMode) {
            if (!myInputs.getKeyboard().isKeyDown(KeyType.S))
                return;
        }

        // O(D * (S + D)) --> every dynamic object with every static and dynamic object
        int index = 0;
        for (final GameObject dynObject : myGameWorld.getDynamicObjects())  {
            if (dynObject instanceof final Rigid2D p) {
//                myCache.set(0, 0.1);
//                myCache.mul(p.getMass());
//                p.applyLinearForce(myCache);
                myCollisionManager.detectCollisions(p, index);
//                System.out.println(p.getLinearVelocity().getMagnitude());
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
