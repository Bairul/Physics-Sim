package com.physicsim.game.controller;

import com.physicsim.game.controller.input.InputController;
import com.physicsim.game.utility.Vector2;
import com.physicsim.game.view.DrawCanvas;
import com.physicsim.game.view.GameScreen;
import com.physicsim.game.view.GameplayScreen;
import com.vaadin.flow.component.UI;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Main class that starts a thread and runs the program.
 *
 * @author Bairu Li
 */
public class VaadinGameRoot {
    /** The default frames per second that the game runs on. */
    private static final int FPS = 60;
    /** Game canvas for graphics. */
    private final DrawCanvas myCanvas;
    /** Spring boot service that creates the game loop. */
    private final ScheduledExecutorService myExecutorService;
    /** Reference to the Ui for graphics at the start. */
    private UI myUi;

    public VaadinGameRoot(final DrawCanvas canvas, final Vector2 theOrigin, final InputController input) {
        myCanvas = canvas;
        myExecutorService = Executors.newSingleThreadScheduledExecutor();

        GameScreen.setOrigin(theOrigin);
        GameScreen.setCanvas(myCanvas);

        // create and set the screen of the game
        final GameScreen myGameplayScreen = new GameplayScreen("GameplayScreen", input);
        GameScreen.setCurrentScreen(myGameplayScreen);
    }

    /**
     * Starts the game loop.
     */
    public void start() {
        myUi = UI.getCurrent();
        myExecutorService.scheduleAtFixedRate(() -> {
            try {
                tick();
                render();
            } catch (final Exception theE) {
                theE.printStackTrace();
            }
        }, 0, 1000 / FPS, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops the game loop.
     */
    public void stop() {
        myExecutorService.shutdownNow();
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
        if (myUi != null) {
            myUi.access(() -> {
                myCanvas.clearRect(0,0, myCanvas.getCanvasWidth(), myCanvas.getCanvasHeight());

                if (GameScreen.getCurrentScreen() != null) {
                    GameScreen.getCurrentScreen().render(myCanvas);
                }
            });
        } else {
            System.err.println("UI.getCurrent() is null. Cannot render :(");
        }
    }
}

