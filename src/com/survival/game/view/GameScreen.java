package com.survival.game.view;

import com.survival.game.utility.Vector2;

import java.awt.Graphics;

public abstract class GameScreen {
    private static GameScreen myCurrentScreen;

    private final String myName;
    protected static Vector2 myGameSize;

    public GameScreen(final String theName) {
        myName = theName;
    }

    public String getName() {
        return myName;
    }

    @Override
    public boolean equals(final Object theO) {
        if (!(theO instanceof GameScreen gs)) {
            return false;
        }
        return myName.equals(gs.myName);
    }

    public static GameScreen getCurrentScreen() {
        return myCurrentScreen;
    }

    public static void setCurrentScreen(final GameScreen theCurrentScreen) {
        myCurrentScreen = theCurrentScreen;
    }

    public static void setGameSize(Vector2 theGameSize) {
        myGameSize = new Vector2(theGameSize);
    }

    public static Vector2 getGameSize() {
        return myGameSize;
    }

    public abstract void tick();
    public abstract void render(final Graphics theG);
}
