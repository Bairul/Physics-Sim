package com.physicsim.game;

import com.physicsim.game.controller.GameRoot;
import org.teavm.jso.browser.Window;

/**
 * Class for launching the program.
 *
 * @author Bairu Li
 */
public final class Launcher {
    private Launcher() {}

    public static void main(String[] args) {
        final GameRoot game = new GameRoot();
        game.start();

        Window.current().addEventListener("beforeunload", evt -> {
            game.stop();
        });
    }
}
