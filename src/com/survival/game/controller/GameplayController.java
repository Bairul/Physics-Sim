package com.survival.game.controller;

import com.survival.game.controller.input.ClickType;
import com.survival.game.controller.input.InputController;
import com.survival.game.model.*;
import com.survival.game.utility.CheckCollide;
import com.survival.game.utility.Vector2;
import com.survival.game.view.GameScreen;

import java.util.List;

public class GameplayController {
    private static final int myInitialVelocity = 30;
    /** World that holds entities and other stuff. */
    private GameWorld gameWorld;
    /** A reusable vector to save some memory. */
    private Vector2 myCache;
    /** The boundary of the screen. */
    private final InputController myInputs;
    private final Vector2 myBoundary;
    public GameplayController(final InputController theInput) {
        myInputs = theInput;
        gameWorld = new GameWorld();
        myBoundary = new Vector2(GameScreen.getWidth() >> 1, GameScreen.getHeight() >> 1);
        // origin of entity
        myCache = new Vector2();

        // System.out.println("Initial Velocity Conditions:\nLeft: " + te.getVelocity() + "\nRight: " + te2.getVelocity() + "\nTop: " + te3.getVelocity());
    }

    /**
     * Updates the state of the game.
     *
     */
    public void update() {
        // add object with mouse click
        if (myInputs.getMouse().isLeftLifted()) {
            myCache.set(myInputs.getMousePos());
            VerletEntity te = new VerletEntity(myCache, 20);
//            te.getVelocity().set(2, 0);
//            te.getVelocity().set(Math.random() * myInitialVelocity - (myInitialVelocity >> 1), Math.random() * myInitialVelocity - (myInitialVelocity >> 1));
//            te.getOldPosition().set(myCache.getX() - 5, myCache.getY() - 5);
            gameWorld.addGameObject(te);
            myInputs.getMouse().offLeftLifted();
        }

        if (gameWorld.getObjects() != null) {
            for (GameObject go : gameWorld.getObjects()) {
//                if (go instanceof MovableObject) {
//                    MovableObject mo = (MovableObject) go;
//                    myCache.set(0, 1);
//                    // gravity
//                    mo.applyImpulse(myCache, 1);
//                    bounceOfWall(mo);
//                }
                if (go instanceof VerletObject vo) {
                    myCache.set(0, 1);
                    vo.applyForce(myCache);
                    bounceOfWallVerlet(vo);
                }
                go.update();
            }
//            CheckCollide.checkForCollisions(gameWorld.getObjects(), myCache);
        }
    }

    /**
     * Detects if a movable object is colliding with the edge of the screen.
     * Bounces the object off of the screen if it hits it.
     *
     * @param theMO the moving obejct
     */
    private void bounceOfWall(final MovableObject theMO) {
        myBoundary.set(myBoundary.getX() - theMO.getRadius(), myBoundary.getY() - theMO.getRadius());
        if (Math.abs(theMO.getPosition().getX()) > myBoundary.getX()) {
            theMO.getPosition().setX(myBoundary.getX() * Math.signum(theMO.getPosition().getX()));
            theMO.getVelocity().setX(theMO.getVelocity().getX() * -1);
        }
        if (Math.abs(theMO.getPosition().getY()) > myBoundary.getY()) {
            theMO.getPosition().setY(myBoundary.getY() * Math.signum(theMO.getPosition().getY()));
            theMO.getVelocity().setY(theMO.getVelocity().getY() * -1);
        }
        myBoundary.set(myBoundary.getX() + theMO.getRadius(), myBoundary.getY() + theMO.getRadius());
    }

    private void bounceOfWallVerlet(final VerletObject theVO) {
        myBoundary.set(myBoundary.getX() - theVO.getRadius(), myBoundary.getY() - theVO.getRadius());
        if (Math.abs(theVO.getPosition().getY()) > myBoundary.getY()) {
            theVO.getPosition().setY(2 * Math.signum(theVO.getPosition().getY()) * myBoundary.getY() - theVO.getPosition().getY());
            theVO.getOldPosition().setY(2 * Math.signum(theVO.getOldPosition().getY()) * myBoundary.getY() - theVO.getOldPosition().getY());
        }
        if (Math.abs(theVO.getPosition().getX()) > myBoundary.getX()) {
            theVO.getPosition().setX(2 * Math.signum(theVO.getPosition().getX()) * myBoundary.getX() - theVO.getPosition().getX());
            theVO.getOldPosition().setX(2 * Math.signum(theVO.getOldPosition().getX()) * myBoundary.getX() - theVO.getOldPosition().getX());
        }
        myBoundary.set(myBoundary.getX() + theVO.getRadius(), myBoundary.getY() + theVO.getRadius());
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
