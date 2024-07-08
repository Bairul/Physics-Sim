package com.physicsim.game.controller;

import com.physicsim.game.controller.input.ClickType;
import com.physicsim.game.controller.input.KeyType;
import com.physicsim.game.model.*;
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

//        VerletBox box = new VerletBox(-300, -100, 100, 10);
//        box.addInputListener(myInputs);
//        myGameWorld.addGameObject(box);

        // testing boundary
//        myCache.set(0, 0);
//        Boundary rectangle = new Boundary(myCache,
//                new Vector2(500, 0),
//                new Vector2(0, 50),
//                new Vector2(-500, 0));
//
//        myCache.set(-400, 0);
//        Boundary triangle = new Boundary(myCache,
//                new Vector2(200, 0),
//                new Vector2(0, -200));

        myCache.set(0, 0);
        VerletPoint p1 = new VerletPoint(myCache, 1, true);
        myCache.set(100, 0);
        VerletPoint p2 = new VerletPoint(myCache, 1, true);

        VerletEdge e = new VerletEdge(p1, p2);

//        myGameWorld.addBoundary(rectangle);
//        myGameWorld.addBoundary(triangle);
        myGameWorld.addBinding(e);
    }

    /**
     * Updates the state of the game.
     *
     */
    public void update() {

        if (myInputs.getKeyboard().isKeyHeld(KeyType.S)) {
            System.out.println(myInputs.getMousePos());
        }

        if (myInputs.getMouse().isButtonDown(ClickType.LeftClick)) {
            VerletPoint p1 = new VerletPoint(myInputs.getMousePos(), 1, 4);
//            VerletPoint p2 = new VerletPoint(myInputs.getMousePos().addNew(new Vector2(100, 0)), 1, 4);
//            VerletStick s = new VerletStick(p1, p2);
            myGameWorld.addGameObject(p1);
//            myGameWorld.addGameObject(p2);
//            myGameWorld.addGameObject(s);
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

                    for (final VerletStick s : myGameWorld.getBindings()) {
                        if (s instanceof VerletEdge e) {
                            if (e.rayCast(p.getPosition())) {
                                e.handleCollision(p);
                            }
                        }
                    }
                    continue;
                }
                gameObject.update();
            }
        }
        if (myGameWorld.getBindings() != null) myGameWorld.getBindings().forEach(VerletStick::update);
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
