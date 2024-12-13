package com.physicsim.game.controller;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.view.DrawCanvas;
import com.physicsim.game.view.GameScreen;
import com.physicsim.game.view.GameplayScreen;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VaadinGameRoot {
    /** The default frames per second that the game runs on. */
    private static final int FPS = 60;
    private final DrawCanvas gameCanvas;
    private UI ui;
    private final ScheduledExecutorService executorService;

    public VaadinGameRoot(DrawCanvas canvas) {
        gameCanvas = canvas;
        executorService = Executors.newSingleThreadScheduledExecutor();

        Vector2 origin = new Vector2(gameCanvas.getCanvasWidth(), gameCanvas.getCanvasHeight());
        origin.mul(0.5F);
        GameScreen.setOrigin(origin);
        GameScreen.setCanvas(gameCanvas);

        // create and set the screen of the game
        final GameScreen myGameplayScreen = new GameplayScreen("GameplayScreen");
        GameScreen.setCurrentScreen(myGameplayScreen);
    }

    /**
     * Starts the game loop.
     */
    public void start() {
        ui = UI.getCurrent();
        executorService.scheduleAtFixedRate(() -> {
            try {
                tick();
                render();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1000 / FPS, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops the game loop.
     */
    public void stop() {
        executorService.shutdownNow();
    }

    /**
     * Update the game state.
     */
    private void tick() {
        if (GameScreen.getCurrentScreen() != null) {
            GameScreen.getCurrentScreen().tick();
        }
    }

    /**
     * Render the game state to the UI.
     */
    private void render() {
        if (ui != null) {
            ui.access(() -> {
                gameCanvas.clearRect(0,0, gameCanvas.getCanvasWidth(), gameCanvas.getCanvasHeight());

                if (GameScreen.getCurrentScreen() != null) {
                    GameScreen.getCurrentScreen().render(gameCanvas);
                }
            });
//            ui.push();
        } else {
            System.err.println("UI.getCurrent() is null. Cannot render.");
        }
    }
}

