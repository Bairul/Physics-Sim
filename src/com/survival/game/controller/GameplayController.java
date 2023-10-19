package com.survival.game.controller;

import com.survival.game.model.GameObject;
import com.survival.game.model.GameWorld;
import com.survival.game.model.MovableObject;
import com.survival.game.model.TempEntity;
import com.survival.game.utility.Vector2;
import com.survival.game.view.GameScreen;

import java.util.List;

public class GameplayController {
    /** World that holds entities and other stuff. */
    private GameWorld gameWorld;
    /** A reusable vector to save some memory. */
    private Vector2 myCache;
    /** The boundary of the screen. */
    private final Vector2 myBoundary;
    public GameplayController() {
        gameWorld = new GameWorld();
        myBoundary = new Vector2(GameScreen.getGameSize().intX() >> 1, GameScreen.getGameSize().intY() >> 1);
        // origin of entity
        myCache = new Vector2(0, 0);

        for (int i = 0; i < 4; i++) {
            // creates new entity with random mass
            TempEntity te = new TempEntity(myCache, (float) Math.random() * 5 + 1);
            // sets random initial velocity of entity
            te.getVelocity().set(Math.random() * 10 - 5, Math.random() * 10 - 5);
            // cache stores a random x value
            myCache.setX(Math.random() * myBoundary.getX());

            gameWorld.addGameObject(te);
        }
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
                    // gravity
//                    myCache.set(0, 0.3);
//                    mo.applyImpulse(myCache, 1);
                    bounceOfWall(mo);
                }
                go.update();
            }
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
