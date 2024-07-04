package com.physicsim.game.controller;

/**
 * Class for launching the program.
 *
 * @author Bairu Li
 */
public final class Launcher {
    private Launcher() {}

    public static void main(final String[] theArgs) {
        GameRoot game = new GameRoot();
        javax.swing.SwingUtilities.invokeLater(game::start);
    }
}
