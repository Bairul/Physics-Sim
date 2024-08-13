package com.physicsim.game.controller;

import com.physicsim.game.controller.input.ClickType;
import com.physicsim.game.controller.input.KeyType;
import com.physicsim.game.model.*;
import com.physicsim.game.model.collision.CollisionManager;
import com.physicsim.game.model.particle.Particle;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.model.rigidbody.Box;
import com.physicsim.game.model.rigidbody.RegularPolygon;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.model.rigidbody.RigidCircle;
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

//        myCache.set(-257, 36);
//        RegularPolygon db = new RegularPolygon(myCache, 4, 100, 20);
//        db.setPhysics(true);
//        db.setAngularPosition(Math.toRadians(-45));
//        myGameWorld.addDynamicObject(db);

        // static polygon
//        myCache.set(-300, GameWorld.SCREEN_BOUNDARY.getY() - 200);
//        RegularPolygon b = new RegularPolygon(myCache, 4, 200, 1);
//        b.setAngularPosition(Math.toRadians(45));
//        myGameWorld.addStaticObject(b);

        // walls
        myCache.set(-400, GameWorld.SCREEN_BOUNDARY.getY() - 300);
        myGameWorld.addStaticObject(new Box(myCache, 200, 200, 1));
//        myCache.set(-GameWorld.SCREEN_BOUNDARY.getX(), -GameWorld.SCREEN_BOUNDARY.getY() - 50);
//        myGameWorld.addStaticObject(new Box(myCache, GameScreen.getWidth(), 50, 1));
//        myCache.set(-GameWorld.SCREEN_BOUNDARY.getX() - 50, -GameWorld.SCREEN_BOUNDARY.getY());
//        myGameWorld.addStaticObject(new Box(myCache, 50, GameScreen.getHeight(), 1));
//        myCache.set(GameWorld.SCREEN_BOUNDARY.getX(), -GameWorld.SCREEN_BOUNDARY.getY());
//        myGameWorld.addStaticObject(new Box(myCache, 50, GameScreen.getHeight(), 1));
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
            // new Vector2(-257, 36)
            RegularPolygon b = new RegularPolygon(myInputs.getMousePos(), 4, 100, 100);
            b.setPhysics(true);
            b.setAngularPosition(Math.toRadians(-40));
//            b.setAngularVelocity(Math.toRadians(5));
            myGameWorld.addDynamicObject(b);
//            myGameWorld.addDynamicObject(new Particle(myInputs.getMousePos(), 1, 4));
        }

        if (myInputs.getKeyboard().isKeyHeld(KeyType.Space)) {
            myGameWorld.clearDynamicObjects();
        }

        if (debugMode) {
            if (!myInputs.getKeyboard().isKeyDown(KeyType.S))
                return;
        }

        // O(D * (S + D)) --> every dynamic object with every static and dynamic object
        for (final GameObject dynObject : myGameWorld.getDynamicObjects())  {
            if (dynObject instanceof final VerletObject p) {
                myCache.set(GameWorld.GRAVITY);
                myCache.mul(p.getMass());
                p.applyForce(myCache);
                p.bounceOffBoundary(GameWorld.SCREEN_BOUNDARY);
                myCollisionManager.testAndHandle(p);
            }
            else if (dynObject instanceof final RigidBody r) {
                myCache.set(GameWorld.GRAVITY);
                myCache.mul(r.getMass());
                r.applyLinearForce(myCache);
                myCollisionManager.testAndHandle(r);
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
