package com.survival.game.controller;

import com.survival.game.controller.input.ClickType;
import com.survival.game.controller.input.InputController;
import com.survival.game.model.GameObject;
import com.survival.game.model.GameWorld;
import com.survival.game.model.MovableObject;
import com.survival.game.model.TempEntity;
import com.survival.game.utility.CheckCollide;
import com.survival.game.utility.Vector2;
import com.survival.game.view.GameScreen;

import java.util.List;

public class GameplayController {
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
        myCache = new Vector2(-250, 0);
        final int initialVelocity = 20;

        TempEntity te = new TempEntity(myCache, 3);
        myCache.set(250, 0);
        TempEntity te2 = new TempEntity(myCache, 6);
        myCache.set(0, -200);
        TempEntity te3 = new TempEntity(myCache, 10);

        te.getVelocity().set(Math.random() * initialVelocity - (initialVelocity >> 1), Math.random() * initialVelocity - (initialVelocity >> 1));
        te2.getVelocity().set(Math.random() * initialVelocity - (initialVelocity >> 1), Math.random() * initialVelocity - (initialVelocity >> 1));
        te3.getVelocity().set(Math.random() * initialVelocity - (initialVelocity >> 1), Math.random() * initialVelocity - (initialVelocity >> 1));

//        te.getVelocity().set(-18.48703, -19.09471);
//        te2.getVelocity().set(6.1653986, 13.285335);
//        te3.getVelocity().set(1.1543714, -16.001493);

        gameWorld.addGameObject(te);
        gameWorld.addGameObject(te2);
        gameWorld.addGameObject(te3);

        System.out.println("Initial Velocity Conditions:\nLeft: " + te.getVelocity() + "\nRight: " + te2.getVelocity() + "\nTop: " + te3.getVelocity());
        // bug happens at these conditions
//        Left: (-18.48703, -19.09471)
//        Right: (6.1653986, 13.285335)
//        Top: (1.1543714, -16.001493)
    }

    /**
     * Updates the state of the game.
     *
     */
    public void update() {
        if (gameWorld.getObjects() != null) {
            for (GameObject go : gameWorld.getObjects()) {
                if (go instanceof MovableObject) {
                    MovableObject mo = (MovableObject) go;

                    // horizontal wind (applied when mouse is pressed)
//                    if (myInputs.getMouse().getButton() == ClickType.LeftClick) {
//                        myCache.set(2, 0);
//                        mo.applyImpulse(myCache, 1);
//                    }
                    bounceOfWall(mo);
                }

                go.update();
            }
            CheckCollide.checkForCollisions(gameWorld.getObjects(), myCache);

            myInputs.getMouse().offLeftLifted();
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

    /**
     * Gets the list of game objects.
     *
     * @return list of game objects
     */
    public List<GameObject> getObjects() {
        return gameWorld.getObjects();
    }
}
