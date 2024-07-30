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
     * @param theG the graphics
     */
    @Override
    public void render(final Graphics theG) {
        // must have update graphics
        myRenderer.updateGraphics(theG);
        final GameWorld gw = myGameplayController.getGameWorld();

        gw.getDynamicObjects().forEach(go -> go.accept(myRenderer));
        gw.getStaticObjects().forEach(b -> b.accept(myRenderer));
    }
}
