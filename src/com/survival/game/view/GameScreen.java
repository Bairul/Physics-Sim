package com.survival.game.view;

import com.survival.game.utility.Vector2;

import java.awt.Graphics;

public abstract class GameScreen {
    /**
     * Tracks the current screen that the game is on. It can change to potentially different screens like menu or options.
     */
    private static GameScreen myCurrentScreen;
    /** The name of the screen. */
    private final String myName;
    /** Size of the screen in case I want to change it. */
    protected static Vector2 myGameSize;

    /**
     * Constructs a screen.
     *
     * @param theName the name of the screen
     */
    public GameScreen(final String theName) {
        myName = theName;
    }

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
        if (!(theO instanceof GameScreen gs)) {
            return false;
        }
        return myName.equals(gs.myName);
    }

    /**
     * Gets the current screen that active.
     * @return the current screen
     */
    public static GameScreen getCurrentScreen() {
        return myCurrentScreen;
    }

    /**
     * Changes the current active screen.
     * @param theCurrentScreen the new screen to change to
     */
    public static void setCurrentScreen(final GameScreen theCurrentScreen) {
        myCurrentScreen = theCurrentScreen;
    }

    /**
     * Changes the size of the screen.
     * @param theGameSize a vector with the new size
     */
    public static void setGameSize(Vector2 theGameSize) {
        myGameSize = new Vector2(theGameSize);
    }

    /**
     * Gets the size of the screen.
     * @return
     */
    public static Vector2 getGameSize() {
        return myGameSize;
    }

    public abstract void tick();
    public abstract void render(final Graphics theG);
}
