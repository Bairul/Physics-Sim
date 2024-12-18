package com.physicsim.game.controller;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.view.DrawCanvas;
import com.physicsim.game.view.GameScreen;
import com.physicsim.game.view.GameplayScreen;
import org.teavm.jso.browser.Window;

/**
 * Main class that starts a thread and runs the program.
 *
 * @author Bairu Li
 */
public final class GameRoot {
    /** The default frames per second that the game runs on. */
    private static final int FPS = 60;
    private static final double FRAMES_PER_MS = 1000.0 / FPS;

    /** The scale of the canvas. */
    private static final int SCALE = 80;
    /** Game canvas for graphics. */
    private final DrawCanvas myGameCanvas;
    private int loopId;
    private double lastTime;
    private double delta;


    /**
     * Constructor that initializes the game's rendering.
     */
    public GameRoot() {
        // creates the game canvas and set the aspect ratio, default is 16:9
        final Vector2 vector = new Vector2(16, 9);
        myGameCanvas = new DrawCanvas(vector, SCALE);

        // Set Game Origin at the center of the screen
        vector.set(myGameCanvas.getWidth(), myGameCanvas.getHeight());
        vector.mul(0.5F);
        GameScreen.setOrigin(vector);
        GameScreen.setCanvas(myGameCanvas);

        // create and set the screen of the game
        final GameScreen myGameplayScreen = new GameplayScreen("GameplayScreen");
        GameScreen.setCurrentScreen(myGameplayScreen);
    }

    /**
     * Game loop that runs the game at the set FPS.
     */
    private void run() {
        double now = System.currentTimeMillis();
        delta += (now - lastTime) / FRAMES_PER_MS;

        while (delta >= 1) {
            tick();
            render();
            delta--;
        }

        lastTime = now;
    }

    /**
     * Updates the state of the game.
     */
    private void tick() {
        if (GameScreen.getCurrentScreen() != null) {
            GameScreen.getCurrentScreen().tick();
        }
    }

    /**
     * Renders the state of the game.
     */
    private void render() {
        myGameCanvas.clearRect(0,0, myGameCanvas.getWidth(), myGameCanvas.getHeight());

        if (GameScreen.getCurrentScreen() != null) {
            GameScreen.getCurrentScreen().render();
        }
    }

    /**
     * Starts the game on a new thread.
     */
    public void start() {
        lastTime = System.currentTimeMillis();
        loopId = Window.setInterval(this::run, 0);
    }

    /**
     * Ends the game and ensures thread safety and consistency.
     */
    public void stop() {
        Window.clearInterval(loopId);
    }
}
