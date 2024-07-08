package com.physicsim.game.view;

import com.physicsim.game.controller.GameplayController;
import com.physicsim.game.controller.input.InputController;
import com.physicsim.game.model.GameWorld;
import com.physicsim.game.visitor.GameObjectRenderer;

import java.awt.Graphics;

/**
 * The gameplay screen where the user plays on.
 *
 * @author Bairu Li
 */
public class GameplayScreen extends GameScreen {
    /** Gameplay Controller. */
    private final GameplayController myGameplayController;
    /** Game renderer. */
    private final GameObjectRenderer myRenderer;

    /**
     * Constructs the screen for the gameplay.
     *
     * @param theName name of screen
     */
    public GameplayScreen(final String theName) {
        super(theName);

        myGameplayController = new GameplayController(new InputController(myCanvas, myOrigin));
        myRenderer = new GameObjectRenderer(myOrigin);
    }

    /**
     * Updates the gameplay.
     */
    @Override
    public void tick() {
        myGameplayController.update();
    }

    /**
     * Renders the gameplay by updating the graphics and drawing each game object.
     * @param theG
     */
    @Override
    public void render(final Graphics theG) {
        // must have update graphics
        myRenderer.updateGraphics(theG);
        GameWorld gw = myGameplayController.getGameWorld();

        if (gw.getObjects() != null) gw.getObjects().forEach(go -> go.accept(myRenderer));
        if (gw.getBindings() != null) gw.getBindings().forEach(b -> b.accept(myRenderer));
    }
}
