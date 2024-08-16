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

//        myCache.set(340, 11);
//        myCache.set(100, -200);
//        RegularPolygon db = new RegularPolygon(myCache, 4, 50, 100);
//        db.setDynamics(true);
//        db.rotate(Math.toRadians(45));
//        db.setLinearVelocity(new Vector2(1, 4));
//        db.setAngularVelocity(Math.toRadians(5));
//        myGameWorld.addDynamicObject(db);

//        myCache.set(-282, -295);
//        RigidCircle c = new RigidCircle(myCache, 50, 1);
//        c.setDynamics(true);
//        myCache.set(0, 6);
//        c.setLinearVelocity(myCache);
//        myGameWorld.addDynamicObject(c);

//        myCache.set(50, -400);
//        RigidCircle dc = new RigidCircle(myCache, 50, 50);
//        dc.setDynamics(true);
//        myCache.set(2,0);
//        dc.setLinearVelocity(myCache);
//        myGameWorld.addDynamicObject(dc);
//
        myCache.set(300, 0);
        RigidCircle dc2 = new RigidCircle(myCache, 100, 50);
        dc2.setDynamics(true);
        myCache.set(-2,0);
        dc2.setLinearVelocity(myCache);
        myGameWorld.addDynamicObject(dc2);

        // static polygon
        myCache.set(-200, GameWorld.SCREEN_BOUNDARY.getY() - 300);
        RegularPolygon b = new RegularPolygon(myCache, 4, 200, 100);
        b.rotate(Math.toRadians(45));
        myGameWorld.addStaticObject(b);

//        myCache.set(200, GameWorld.SCREEN_BOUNDARY.getY() - 300);
//        RegularPolygon b2 = new RegularPolygon(myCache, 3, 300, 100);
//        b2.rotate(Math.toRadians(20));
//        b2.setDynamics(true);
//        myGameWorld.addDynamicObject(b2);

        myCache.set(50, GameWorld.SCREEN_BOUNDARY.getY() - 300);
        RigidCircle c = new RigidCircle(myCache, 100, 1);
        myGameWorld.addStaticObject(c);

//        myCache.set(-500, GameWorld.SCREEN_BOUNDARY.getY() - 300);
//        myGameWorld.addStaticObject(new Box(myCache, 1000, 50, 1));

        // walls
        myCache.set(-GameWorld.SCREEN_BOUNDARY.getX(), GameWorld.SCREEN_BOUNDARY.getY());
        myGameWorld.addStaticObject(new Box(myCache, GameScreen.getWidth(), 50, 1));
        myCache.set(-GameWorld.SCREEN_BOUNDARY.getX() - 50, -GameWorld.SCREEN_BOUNDARY.getY());
        myGameWorld.addStaticObject(new Box(myCache, 50, GameScreen.getHeight(), 1));
        myCache.set(GameWorld.SCREEN_BOUNDARY.getX(), -GameWorld.SCREEN_BOUNDARY.getY());
        myGameWorld.addStaticObject(new Box(myCache, 50, GameScreen.getHeight(), 1));

//        myCache.set(-GameWorld.SCREEN_BOUNDARY.getX(), -GameWorld.SCREEN_BOUNDARY.getY() - 50);
//        myGameWorld.addStaticObject(new Box(myCache, GameScreen.getWidth(), 50, 1));
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
//            RegularPolygon b = new RegularPolygon(myInputs.getMousePos(), 4, 50, 100);
//            b.setDynamics(true);
//            b.rotate(Math.toRadians(45));
//            myGameWorld.addDynamicObject(b);
//            b.setLinearVelocity(new Vector2(1, 4));
//            b.setAngularVelocity(Math.toRadians(5));

//            Box b = new Box(myInputs.getMousePos(), 200, 50, 100);
//            b.setDynamics(true);
//            b.rotate(Math.toRadians(45));
//            b.setLinearVelocity(new Vector2(1, 4));
//            b.setAngularVelocity(Math.toRadians(5));
//            myGameWorld.addDynamicObject(b);

            Particle p = new Particle(myInputs.getMousePos(), 5, 4);
//            myCache.set(2, 1);
//            p.setVelocity(myCache);
            p.setDynamics(true);
            myGameWorld.addDynamicObject(p);

//            RigidCircle c = new RigidCircle(myInputs.getMousePos(), 50, 50);
//            c.setDynamics(true);
//            myCache.set(1, 0);
//            myGameWorld.addDynamicObject(c);
//            c.setAngularVelocity(0.1);
//            c.setLinearVelocity(myCache);
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
            if (dynObject instanceof final VerletObject p) {
                myCache.set(GameWorld.GRAVITY);
                myCache.mul(p.getMass());
                p.applyForce(myCache);
                myCollisionManager.testAndHandle(p, index);
            }
            else if (dynObject instanceof final RigidBody r) {
                myCache.set(GameWorld.GRAVITY);
                myCache.mul(r.getMass());
                r.applyLinearForce(myCache);
                myCollisionManager.testAndHandle(r, index);
            }
            else if (dynObject instanceof final RigidCircle c) {
                myCache.set(GameWorld.GRAVITY);
                myCache.mul(c.getMass());
                c.applyLinearForce(myCache);
//                c.applyTorque(0.2);
                myCollisionManager.testAndHandle(c, index);
            }
            index++;
        }

        if (debugMode) return;

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
