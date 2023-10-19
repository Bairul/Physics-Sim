package com.survival.game.view;

import com.survival.game.controller.GameplayController;
import com.survival.game.utility.Vector2;
import com.survival.game.visitor.GameObjectRenderer;

import java.awt.Graphics;

public class GameplayScreen extends GameScreen {

    private final GameplayController myGameplayController;
    private final GameObjectRenderer myRenderer;

    public GameplayScreen(final String theName, final Vector2 theOrigin) {
        super(theName);

        myGameplayController = new GameplayController();
        myRenderer = new GameObjectRenderer(theOrigin);
    }

    @Override
    public void tick() {
        myGameplayController.update();
    }

    @Override
    public void render(final Graphics theG) {
        myRenderer.updateGraphics(theG);

        if (myGameplayController.getObjects() != null) myGameplayController.getObjects().forEach(go -> go.accept(myRenderer));
    }
}
