package com.survival.game.controller;

/**
 * Class for launching the program.
 */
public final class Launcher {
    private Launcher() {}

    public static void main(final String[] theArgs) {
        GameRoot game = new GameRoot();
        javax.swing.SwingUtilities.invokeLater(game::start);
    }
}
