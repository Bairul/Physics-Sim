package com.survival.game.controller;

import com.survival.game.model.GameObject;
import com.survival.game.model.GameWorld;
import com.survival.game.model.MovableObject;
import com.survival.game.model.TempEntity;
import com.survival.game.utility.Vector2;
import com.survival.game.view.GameScreen;

import java.util.List;

public class GameplayController {
    private static final int DEFAULT_SIZE = 50;
    private static final int DEFAULT_RADIUS = DEFAULT_SIZE / 2;
    private GameWorld gameWorld;
    private Vector2 myCache;
    private final Vector2 myBoundary;
    public GameplayController() {
        gameWorld = new GameWorld();
        myBoundary = new Vector2((GameScreen.getGameSize().intX() >> 1) - DEFAULT_RADIUS, (GameScreen.getGameSize().intY() >> 1) - DEFAULT_RADIUS);
        // origin of entity
        myCache = new Vector2(0, 0);

        for (int i = 0; i < 4; i++) {
            TempEntity te = new TempEntity(myCache, DEFAULT_SIZE);
            te.getVelocity().set(Math.random() * 10 - 5, Math.random() * 10 - 5);
            gameWorld.addGameObject(te);
        }
    }

    public void update() {
        if (gameWorld.getObjects() != null) {
            for (GameObject go : gameWorld.getObjects()) {
                if (go instanceof MovableObject) {
                    MovableObject mo = (MovableObject) go;
                    // gravity
                    myCache.set(0, 0.3);
                    mo.applyImpulse(myCache, 1);
                    bounceOfWall(mo);
                }
                go.update();
            }
        }
    }

    private void bounceOfWall(final MovableObject mo) {
        if (Math.abs(mo.getPosition().getX()) > myBoundary.getX()) {
            mo.getPosition().setX(myBoundary.getX() * Math.signum(mo.getPosition().getX()));
            mo.getVelocity().setX(mo.getVelocity().getX() * -1);
        }
        if (Math.abs(mo.getPosition().getY()) > myBoundary.getY()) {
            mo.getPosition().setY(myBoundary.getY() * Math.signum(mo.getPosition().getY()));
            mo.getVelocity().setY(mo.getVelocity().getY() * -1);
        }
    }

    public List<GameObject> getObjects() {
        return gameWorld.getObjects();
    }
}
