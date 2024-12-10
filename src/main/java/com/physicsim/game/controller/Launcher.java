package com.physicsim.game.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class for launching the program.
 *
 * @author Bairu Li
 */

@SpringBootApplication
public class Launcher {
    public static void main(final String[] theArgs) {
        SpringApplication.run(Launcher.class, theArgs);
    }
}
