package com.physicsim.game.controller;

import com.physicsim.game.model.*;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.controller.input.InputController;
import com.physicsim.game.view.GameScreen;

import java.util.List;

public class GameplayController {
    private static final Vector2 GRAVITY = new Vector2(0, 1);
    /** The boundary of the screen. */
    private final Vector2 myBoundary;
    /** The user inputs. */
    private final InputController myInputs;
    /** World that holds entities and other stuff. */
    private GameWorld gameWorld;
    /** A reusable vector to save some memory. */
    private Vector2 myCache;

    public GameplayController(final InputController theInput) {
        myInputs = theInput;
        gameWorld = new GameWorld();
        // boundary will be at the center of the game screen
        myBoundary = new Vector2(GameScreen.getWidth() >> 1, GameScreen.getHeight() >> 1);
        myCache = new Vector2();

        // Testing sticks
//        myCache.set(-50, -60);
//        VerletPoint p1 = new VerletPoint(myCache, 1);
//        myCache.set(30, -50);
//        VerletPoint p2 = new VerletPoint(myCache, 1);
//        myCache.set(40, -120);
//        VerletPoint p3 = new VerletPoint(myCache, 1);
//        myCache.set(-40, -130);
//        VerletPoint p4 = new VerletPoint(myCache, 1);
//        myCache.set(-30, 0);
//        VerletPoint p5 = new VerletPoint(myCache, 1, true);
//
//        VerletStick s1 = new VerletStick(p1, p2);
//        VerletStick s2 = new VerletStick(p2, p3);
//        VerletStick s3 = new VerletStick(p3, p4);
//        VerletStick s4 = new VerletStick(p4, p1);
//        VerletStick s5 = new VerletStick(p3, p1);
//        VerletStick s6 = new VerletStick(p4, p2);
//        VerletStick s7 = new VerletStick(p3, p5);
//
//        gameWorld.addGameObject(p1);
//        gameWorld.addGameObject(p2);
//        gameWorld.addGameObject(p3);
//        gameWorld.addGameObject(p4);
//        gameWorld.addGameObject(p5);
//
//        gameWorld.addGameObject(s1);
//        gameWorld.addGameObject(s2);
//        gameWorld.addGameObject(s3);
//        gameWorld.addGameObject(s4);
//        gameWorld.addGameObject(s5);
//        gameWorld.addGameObject(s6);
//        gameWorld.addGameObject(s7);
        myCache.set(0, -300);
        VerletPoint p1 = new VerletPoint(myCache, 1, true);
        myCache.set(50, -300);
        VerletPoint p2 = new VerletPoint(myCache, 1);
        myCache.set(100, -300);
        VerletPoint p3 = new VerletPoint(myCache, 1);
        myCache.set(150, -300);
        VerletPoint p4 = new VerletPoint(myCache, 1);
        myCache.set(200, -300);
        VerletPoint p5 = new VerletPoint(myCache, 1);
        myCache.set(250, -300);
        VerletPoint p6 = new VerletPoint(myCache, 1);
        myCache.set(300, -300);
        VerletPoint p7 = new VerletPoint(myCache, 1);

        VerletStick s1 = new VerletStick(p1, p2);
        VerletStick s2 = new VerletStick(p2, p3);
        VerletStick s3 = new VerletStick(p3, p4);
        VerletStick s4 = new VerletStick(p4, p5);
        VerletStick s5 = new VerletStick(p5, p6);
        VerletStick s6 = new VerletStick(p6, p7);

        gameWorld.addGameObject(p1);
        gameWorld.addGameObject(p2);
        gameWorld.addGameObject(p3);
        gameWorld.addGameObject(p4);
        gameWorld.addGameObject(p5);
        gameWorld.addGameObject(p6);
        gameWorld.addGameObject(p7);

        gameWorld.addGameObject(s1);
        gameWorld.addGameObject(s2);
        gameWorld.addGameObject(s3);
        gameWorld.addGameObject(s4);
        gameWorld.addGameObject(s5);
        gameWorld.addGameObject(s6);
    }

    /**
     * Updates the state of the game.
     *
     */
    public void update() {
        // add object with mouse click
//        if (myInputs.getMouse().isLeftLifted()) {
//            myCache.set(myInputs.getMousePos());
//            VerletEntity ve = new VerletEntity(myCache, 20, myBoundary);
////            te.getVelocity().set(Math.random() * myInitialVelocity - (myInitialVelocity >> 1), Math.random() * myInitialVelocity - (myInitialVelocity >> 1));
////            ve.getOldPosition().set(myCache.getX() - 5, myCache.getY() - 5);
//            myCache.set(5, 0);
//            ve.setVelocity(myCache);
//
//            gameWorld.addGameObject(ve);
//            myInputs.getMouse().offLeftLifted();
//        }

        if (gameWorld.getObjects() != null) {
            for (GameObject go : gameWorld.getObjects()) {
                if (go instanceof VerletObject vo) {
                    vo.applyForce(GRAVITY);
                    vo.bounceOffBoundary(myBoundary);
                }
                go.update();
            }
        }
    }

    /**
     * Gets the list of game objects.
     *
     * @return list of game objects
     */
    public List<GameObject> getObjects() {
        return gameWorld.getObjects();
    }
}
