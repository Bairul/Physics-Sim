package com.physicsim.game.view;

import com.physicsim.game.utility.Vector2;

import java.awt.Graphics;

/**
 * Functionality of GameScreen should be allowing the switching of screens, i.e. from loading screen to gameplay screen.
 *
 * @author Bairu Li
 */
public abstract class GameScreen {
    // static fields
    /** Tracks the current screen that the game is on. It can change to potentially different screens like menu or options. */
    private static GameScreen myCurrentScreen;
    /** Size of the screen in case I want to change it. */
    protected static GameCanvas myCanvas;
    /** The origin of the screen. Used to offset the origin in the top-left corner of the screen. */
    protected static Vector2 myOrigin;

    // instance fields
    /** The name of the screen. */
    private final String myName;

    /**
     * Constructs a screen.
     *
     * @param theName the name of the screen
     */
    public GameScreen(final String theName) {
        myName = theName;
    }

    // ========= static methods =========
    /**
     * Gets the current screen that active.
     * @return the current screen
     */
    public static GameScreen getCurrentScreen() {
        return myCurrentScreen;
    }

    /**
     * Changes the current active screen. Shouldn't change.
     * @param theCurrentScreen the new screen to change to
     */
    public static void setCurrentScreen(final GameScreen theCurrentScreen) {
        myCurrentScreen = theCurrentScreen;
    }

    /**
     * Sets the canvas.
     * @param theCanvas the game canvas
     */
    public static void setCanvas(final GameCanvas theCanvas) {
        myCanvas = theCanvas;
    }

    /**
     * Sets the origin of the screen.
     * @param theOrigin the origin in a vector
     */
    public static void setOrigin(final Vector2 theOrigin) {
        myOrigin = new Vector2(theOrigin);
    }
    /**
     * Gets the width of the screen.
     * @return the width, but if canvas is not set, it returns 0
     */
    public static int getWidth() {
        if (myCanvas == null) return 0;
        return myCanvas.getWidth();
    }
    /**
     * Gets the height of the screen.
     * @return the height, but if canvas is not set, it returns 0
     */
    public static int getHeight() {
        if (myCanvas == null) return 0;
        return myCanvas.getHeight();
    }

    // ========= instance methods =========
    /**
     * Gets the name of the screen
     * @return the name of the screen
     */
    public String getName() {
        return myName;
    }

    /**
     * Detects if a screen is the same as another screen. Useful for changing screens in the future.
     * @param theO object
     * @return true if the same screen and false otherwise
     */
    @Override
    public boolean equals(final Object theO) {
        if (!(theO instanceof final GameScreen gs)) {
            return false;
        }
        return myName.equals(gs.myName);
    }

    /**
     * Update method.
     */
    public abstract void tick();

    /**
     * Render method.
     * @param theG the graphics
     */
    public abstract void render(final Graphics theG);
}
