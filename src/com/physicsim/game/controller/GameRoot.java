package com.physicsim.game.controller;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.view.GameCanvas;
import com.physicsim.game.view.GameScreen;
import com.physicsim.game.view.GameplayScreen;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * Main class that starts a thread and runs the program.
 *
 * @author Bairu Li
 */
public final class GameRoot implements Runnable {
    /** The default frames per second that the game runs on. */
    private static final int FPS = 60;
    /** The scale of the canvas. */
    private static final int SCALE = 80;
    /** Game canvas for graphics. */
    private final GameCanvas myGameCanvas;
    /** Buffer strategy for frames to render and display. */
    private final BufferStrategy myBufferStrategy;
    /** The thread that the program will run on. */
    private Thread myThread;
    /** State of the game whether it is running or not. */
    private boolean isRunning;

    /**
     * Constructor that initializes the game's rendering.
     */
    public GameRoot() {
        // creates the game canvas and set the aspect ratio, default is 16:9
        final Vector2 vector = new Vector2(16, 9);
        myGameCanvas = new GameCanvas(vector, SCALE, "Physics Simulator");

        // triple buffering to the canvas
        myGameCanvas.getCanvas().createBufferStrategy(3);
        myBufferStrategy = myGameCanvas.getCanvas().getBufferStrategy();

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
    @Override
    public void run() {
        double framesPerNs = 1000000000D / FPS;
        double delta = 0;
        long now;
        long past = System.nanoTime();

        // the game loop
        while (isRunning) {
            now = System.nanoTime();
            delta += (now - past) / framesPerNs;
            past = now;

            while (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
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
        final Graphics g = myBufferStrategy.getDrawGraphics();
        g.clearRect(0,0, myGameCanvas.getWidth(), myGameCanvas.getHeight());

        if (GameScreen.getCurrentScreen() != null) {
            GameScreen.getCurrentScreen().render(g);
        }

        g.dispose();
        myBufferStrategy.show();
    }

    /**
     * Starts the game on a new thread.
     */
    public synchronized void start() {
        if (isRunning) return;

        isRunning = true;
        myThread = new Thread(this);
        myThread.start();
    }

    /**
     * Ends the game and ensures thread safety and consistency.
     */
    public synchronized void stop() {
        if (!isRunning) return;

        isRunning = false;
        try {
            myThread.join();
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
