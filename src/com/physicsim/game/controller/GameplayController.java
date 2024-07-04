package com.physicsim.game.controller;

import com.physicsim.game.model.GameObject;
import com.physicsim.game.model.GameWorld;
import com.physicsim.game.model.VerletEntity;
import com.physicsim.game.model.VerletObject;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.controller.input.InputController;
import com.physicsim.game.view.GameScreen;

import java.util.List;

public class GameplayController {
    private static final int myInitialVelocity = 30;
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
    }

    /**
     * Updates the state of the game.
     *
     */
    public void update() {
        // add object with mouse click
        if (myInputs.getMouse().isLeftLifted()) {
            myCache.set(myInputs.getMousePos());
            VerletEntity ve = new VerletEntity(myCache, 20);
//            te.getVelocity().set(Math.random() * myInitialVelocity - (myInitialVelocity >> 1), Math.random() * myInitialVelocity - (myInitialVelocity >> 1));
//            ve.getOldPosition().set(myCache.getX() - 5, myCache.getY() - 5);
            myCache.set(5, 0);
            ve.setVelocity(myCache);

            gameWorld.addGameObject(ve);
            myInputs.getMouse().offLeftLifted();
        }

        if (gameWorld.getObjects() != null) {
            for (GameObject go : gameWorld.getObjects()) {
                if (go instanceof VerletObject vo) {
                    myCache.set(0, 2);
                    vo.applyForce(myCache);
                    bounceOfWallVerlet(vo);
                }
                go.update();
            }
        }
    }

    /**
     * Detects if a movable object is colliding with the edge of the screen.
     * Bounces the object off of the screen if it hits it.
     *
     * @param theVO the moving obejct
     */
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
