package com.physicsim.game.controller;

import com.physicsim.game.model.*;
import com.physicsim.game.model.mesh.VerletBox;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.controller.input.InputController;
import com.physicsim.game.view.GameScreen;

import java.util.List;

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
    private final Vector2 myCache;

    /**
     * Constructs the gameplay controller.
     * @param theInput
     */
    public GameplayController(final InputController theInput) {
        myInputs = theInput;
        myGameWorld = new GameWorld();
        myCache = new Vector2();
        // boundary will be at the center of the game screen
        GameWorld.SCREEN_BOUNDARY.set(GameScreen.getWidth() >> 1, GameScreen.getHeight() >> 1);
        GameWorld.GRAVITY.set(0, 0.25);

        // create test boundary
        Boundary bound = new Boundary(0, 100,
                new Vector2(GameWorld.SCREEN_BOUNDARY.getX(), 0),
                new Vector2(0, 50),
                new Vector2(-GameWorld.SCREEN_BOUNDARY.getX(), 0));

        myGameWorld.addBoundary(bound);
    }

    /**
     * Updates the state of the game.
     *
     */
    public void update() {
        // add object with mouse click
        if (myInputs.getMouse().isLeftLifted()) {
            myCache.set(myInputs.getMousePos());
            VerletPoint p = new VerletPoint(myCache, 1, true);
            VerletBox box = new VerletBox(myCache.getX(), myCache.getY(), 120, 2);
            VerletStick s = new VerletStick(p, box.getVertices()[3]);

            myGameWorld.addGameObject(p);
            myGameWorld.addGameObject(box);
            myGameWorld.addGameObject(s);
            myInputs.getMouse().offLeftLifted();
        }

        if (myGameWorld.getObjects() != null) myGameWorld.getObjects().forEach(GameObject::update);
        if (myGameWorld.getBoundaries() != null) myGameWorld.getBoundaries().forEach(Boundary::update);
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
