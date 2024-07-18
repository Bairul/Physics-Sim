package com.physicsim.game.controller;

import com.physicsim.game.controller.input.ClickType;
import com.physicsim.game.controller.input.KeyType;
import com.physicsim.game.model.*;
import com.physicsim.game.model.rigidbody.Box;
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
    private final Vector2 myCache;

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

        myCache.set(0, 0);
        Box box = new Box(myCache, 100);
        myGameWorld.addGameObject(box);
    }

    /**
     * Updates the state of the game.
     *
     */
    public void update() {

        if (myInputs.getMouse().isButtonDown(ClickType.LeftClick)) {
            myGameWorld.addGameObject(new VerletPoint(myInputs.getMousePos(), 1, 4));
        }

        if (myInputs.getKeyboard().isKeyDown(KeyType.Space)) {
            myGameWorld.clearGameObjects();
        }
        if (myGameWorld.getObjects() != null) {
            for (final GameObject gameObject : myGameWorld.getObjects())  {
                if (gameObject instanceof VerletPoint p) {
                    myCache.set(0,0.2);
                    myCache.mul(p.getMass());
                    p.applyForce(myCache);
                    p.bounceOffBoundary(GameWorld.SCREEN_BOUNDARY);
                    p.update();
                    continue;
                }
                gameObject.update();
            }
        }

        if (myGameWorld.getBoundaries() != null) myGameWorld.getBoundaries().forEach(GameObject::update);
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
