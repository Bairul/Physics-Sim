package com.survival.game.controller;

import com.survival.game.utility.Vector2;
import com.survival.game.view.GameCanvas;
import com.survival.game.view.GameScreen;
import com.survival.game.view.GameplayScreen;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * Main class that runs the program.
 *
 */
public final class GameRoot implements  Runnable {
    /** The default frames per second that the game runs on. */
    private static final int FPS = 30;
    /** The scale of the canvas. */
    private static final int SCALE = 60;
    /** Game canvas for graphics. */
    private final GameCanvas myGameCanvas;
    /** Buffer strategy for frames to render and display. */
    private final BufferStrategy myBufferStrategy;
    /** The thread that the program will run on. */
    private Thread myThread;
    /** State of the game whether it is running or not. */
    private boolean isRunning;
    /** The game's gameplay screen. */
    private GameScreen myGameplayScreen;
    /** A reusable vector to attempt to save some memory. */
    private final Vector2 myCache;

    /**
     * Constructor that initializes the game's rendering.
     */
    public GameRoot() {
        // the aspect ratio, default is 16:9
        myCache = new Vector2(16, 9);
        myGameCanvas = new GameCanvas(myCache, SCALE, "This Game");
        // triple buffering
        myGameCanvas.getCanvas().createBufferStrategy(3);
        myBufferStrategy = myGameCanvas.getCanvas().getBufferStrategy();
    }

    /**
     * Sets up the current state of the game.
     */
    private void setUp() {
        // Set Game Origin
        myCache.set(myGameCanvas.getWidth(), myGameCanvas.getHeight());
        myCache.mul(0.5F);
        GameScreen.setOrigin(myCache);
        GameScreen.setCanvas(myGameCanvas);

        myGameplayScreen = new GameplayScreen("GameplayScreen");
        GameScreen.setCurrentScreen(myGameplayScreen);
    }

    /**
     * Game loop that runs the game at the set FPS.
     */
    @Override
    public void run() {
        setUp();
        double framesPerNs = 1000000000D / FPS;
        double delta = 0;
        long now;
        long past = System.nanoTime();

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
        Graphics g = myBufferStrategy.getDrawGraphics();
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
     * Ends the game.
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
