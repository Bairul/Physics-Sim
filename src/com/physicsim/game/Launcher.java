package com.physicsim.game;

import com.physicsim.game.controller.GameRoot;

/**
 * Class for launching the program.
 *
 * @author Bairu Li
 */
public final class Launcher {
    private Launcher() {}

    public static void main(final String[] theArgs) {
        final GameRoot game = new GameRoot();
        javax.swing.SwingUtilities.invokeLater(game::start);
    }
}
