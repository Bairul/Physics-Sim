package com.physicsim.game.controller;

import com.physicsim.game.controller.input.ClickType;
import com.physicsim.game.controller.input.KeyType;
import com.physicsim.game.model.*;
import com.physicsim.game.model.particle.Particle;
import com.physicsim.game.model.particle.VerletObject;
import com.physicsim.game.model.rigidbody.Box;
import com.physicsim.game.model.rigidbody.RigidBody;
import com.physicsim.game.model.rigidbody.RigidCircle;
import com.physicsim.game.model.rigidbody.Triangle;
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
    private static final Box myBox = new Box(new Vector2(-300, 0), 100);

    /** The user inputs. */
    private final InputController myInputs;
    /** World that holds entities and other stuff. */
    private final GameWorld myGameWorld;
    /** A reusable vector to save some memory. */
    private final Vector2 myCache;
    private boolean debugMode;

    /**
     * Constructs the gameplay controller.
     * @param theInput the input controller
     */
    public GameplayController(final InputController theInput) {
        myInputs = theInput;
        myGameWorld = new GameWorld();
        myCache = new Vector2();
        // boundary will be at the center of the game screen
        GameWorld.SCREEN_BOUNDARY.set(GameScreen.getWidth() >> 1, GameScreen.getHeight() >> 1);
        GameWorld.GRAVITY.set(0, 0.25);
        debugMode = false;

        myCache.set(0, 0);
        myGameWorld.addStaticObject(new Box(myCache, 200));
//        myCache.set(-400, 0);
//        myGameWorld.addStaticObject(new Triangle(myCache, 200));
//
//        myCache.set(-200, -200);
//        myGameWorld.addDynamicObject(new Particle(myCache, 1, 4));
//        myGameWorld.addDynamicObject(myBox);
        myCache.set(-250, -50);
        myGameWorld.addStaticObject(new RigidCircle(myCache, 200));
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
            myGameWorld.addDynamicObject(new Particle(myInputs.getMousePos(), 1, 4));
        }

        if (debugMode) {
            if (!myInputs.getKeyboard().isKeyDown(KeyType.S))
                return;
        }

        if (myInputs.getKeyboard().isKeyHeld(KeyType.Space)) {
            myGameWorld.clearDynamicObjects();
//            myCache.set(myInputs.getMousePos());
//            myCache.sub(myBox.getCenter());
//            myCache.norm();
//            myBox.translate(myCache);
//            myBox.rotate(1);
        }

        // O(D * S) --> every dynamic object with every static object
        for (final GameObject dynObject : myGameWorld.getDynamicObjects())  {
            if (dynObject instanceof final VerletObject p) {
                myCache.set(GameWorld.GRAVITY);
                myCache.mul(p.getMass());
                p.applyForce(myCache);
                p.bounceOffBoundary(GameWorld.SCREEN_BOUNDARY);
                p.update();

                for (final GameObject staticObject : myGameWorld.getStaticObjects()) {
                    if (staticObject instanceof final RigidBody r) {
                        r.collideAgainst(p);
                    }
                    if (staticObject instanceof final RigidCircle r) {
                        r.collideAgainst(p);
                    }
                }
                continue;
            }

            if (dynObject instanceof final RigidBody r) {
                for (final GameObject staticObject : myGameWorld.getStaticObjects()) {
                    if (staticObject instanceof final RigidBody rs) {
                        rs.collideAgainst(r);
                    }
                }
            }
            dynObject.update();
        }

        myGameWorld.getStaticObjects().forEach(GameObject::update);
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
