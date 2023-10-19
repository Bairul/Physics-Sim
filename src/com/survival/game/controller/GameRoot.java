package com.survival.game.controller;

import com.survival.game.utility.Vector2;
import com.survival.game.view.GameCanvas;
import com.survival.game.view.GameScreen;
import com.survival.game.view.GameplayScreen;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public final class GameRoot implements  Runnable {
    private static final int FPS = 30;
    private static final int SCALE = 60;
    private final GameCanvas myGameCanvas;
    private final BufferStrategy myBufferStrategy;
    private Thread myThread;
    private boolean isRunning;
    private GameScreen myGameplayScreen;
    private final Vector2 myCache;

    public GameRoot() {
        myCache = new Vector2(16, 9);
        myGameCanvas = new GameCanvas(myCache, SCALE, "Test");
        myGameCanvas.getCanvas().createBufferStrategy(3);
        myBufferStrategy = myGameCanvas.getCanvas().getBufferStrategy();
    }

    public void setUp() {
        myCache.set(myGameCanvas.getWidth(), myGameCanvas.getHeight());
        GameScreen.setGameSize(myCache);
        // Game Origin
        myCache.mul(0.5F);

        myGameplayScreen = new GameplayScreen("GameplayScreen", myCache);
        GameScreen.setCurrentScreen(myGameplayScreen);
    }

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

    private void tick() {
        if (GameScreen.getCurrentScreen() != null) {
            GameScreen.getCurrentScreen().tick();
        }
    }

    private void render() {
        Graphics g = myBufferStrategy.getDrawGraphics();
        g.clearRect(0,0, myGameCanvas.getWidth(), myGameCanvas.getHeight());

        if (GameScreen.getCurrentScreen() != null) {
            GameScreen.getCurrentScreen().render(g);
        }

        g.dispose();
        myBufferStrategy.show();
    }

    public synchronized void start() {
        if (isRunning) return;

        isRunning = true;
        myThread = new Thread(this);
        myThread.start();
    }

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
