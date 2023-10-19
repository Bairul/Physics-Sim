package com.survival.game.view;

import com.survival.game.controller.GameplayController;
import com.survival.game.utility.Vector2;
import com.survival.game.visitor.GameObjectRenderer;

import java.awt.Graphics;

/**
 * The gameplay screen where the user plays on.
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

        myGameplayController = new GameplayController();
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

        if (myGameplayController.getObjects() != null) myGameplayController.getObjects().forEach(go -> go.accept(myRenderer));
    }
}
