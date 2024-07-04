package com.physicsim.game.controller;

import com.physicsim.game.model.*;
import com.physicsim.game.model.mesh.VerletBox;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.controller.input.InputController;
import com.physicsim.game.view.GameScreen;

import java.util.List;

public class GameplayController {
    /** The user inputs. */
    private final InputController myInputs;
    /** World that holds entities and other stuff. */
    private final GameWorld gameWorld;
    private final Vector2 myCache;

    public GameplayController(final InputController theInput) {
        myInputs = theInput;
        gameWorld = new GameWorld();
        myCache = new Vector2();
        // boundary will be at the center of the game screen
        GameWorld.BOUNDARY.set(GameScreen.getWidth() >> 1, GameScreen.getHeight() >> 1);
        GameWorld.GRAVITY.set(0, 0.5);

//        VerletBox box = new VerletBox(0, 0, 50, 1);
//        gameWorld.addGameObject(box);
    }

    /**
     * Updates the state of the game.
     *
     */
    public void update() {
        // add object with mouse click
        if (myInputs.getMouse().isLeftLifted()) {
            myCache.set(myInputs.getMousePos());
            VerletBox box = new VerletBox(myCache.getX(), myCache.getY(), 120, 4);
//            te.getVelocity().set(Math.random() * myInitialVelocity - (myInitialVelocity >> 1), Math.random() * myInitialVelocity - (myInitialVelocity >> 1));
//            ve.getOldPosition().set(myCache.getX() - 5, myCache.getY() - 5);

            gameWorld.addGameObject(box);
            myInputs.getMouse().offLeftLifted();
        }

        if (gameWorld.getObjects() != null) {
            for (GameObject go : gameWorld.getObjects()) {
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
