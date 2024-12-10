package com.physicsim.game.controller;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VaadinGameRoot {
    private static final int FPS = 60; // Target frames per second
    private final Div gameCanvas;
    private final ScheduledExecutorService executorService;
    private boolean isRunning;

    public VaadinGameRoot(Div canvas) {
        this.gameCanvas = canvas;
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Starts the game loop.
     */
    public void start() {
        if (isRunning) return;

        isRunning = true;

        executorService.scheduleAtFixedRate(() -> {
            try {
                tick();
                render();
            } catch (Exception e) {
                // Log exceptions to avoid crashing the executor
                e.printStackTrace();
            }
        }, 0, 1000 / FPS, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops the game loop.
     */
    public void stop() {
        if (!isRunning) return;

        isRunning = false;
        executorService.shutdownNow();
    }

    /**
     * Update the game state.
     */
    private void tick() {
        // Add game state update logic here
        System.out.println("Game state updated at: " + System.currentTimeMillis());
    }

    /**
     * Render the game state to the UI.
     */
    private void render() {
        // Render logic here
        UI.getCurrent().access(() -> {
            // Example: Displaying current time as the game's "state"
            gameCanvas.setText("Game rendered at: " + System.currentTimeMillis());
        });
    }
}

