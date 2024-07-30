package com.physicsim.game.controller.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for keyboard input.
 *
 * @author Bairu Li
 */
public class Keyboard extends KeyAdapter {

    /** Storing the state when a key is pressed for the first time. */
    private final boolean[] myKeyDowns;
    /** Storing the state when a key is let go. */
    private final boolean[] myKeyUps;
    /** Storing the state when a key is being held down constantly. */
    private final Set<Integer> myKeyHelds;

    /**
     * Constructs the keyboard.
     */
    public Keyboard() {
        myKeyHelds = new HashSet<>();
        myKeyDowns = new boolean[256];
        myKeyUps = new boolean[256];
    }

    /**
     * Converts the ClickType enum to integers.
     * @param theKey the click type
     * @return the respective integer
     */
    private int enumToInt(final KeyType theKey) {
        return switch (theKey) {
            case W -> KeyEvent.VK_W;
            case A -> KeyEvent.VK_A;
            case S -> KeyEvent.VK_S;
            case D -> KeyEvent.VK_D;
            case Space -> KeyEvent.VK_SPACE;
        };
    }

    /**
     * Determines if a KeyType is currently being held down.
     *
     * @param theKey the key in question
     * @return whether that key is being pressed
     */
    public boolean isKeyHeld(final KeyType theKey) {
        return myKeyHelds.contains(enumToInt(theKey));
    }

    /**
     * Determines if a KeyType has released for the first time. This would only return
     * true the moment when the key has been released. Will return false for subsequent
     * calls until the key is pressed, and then released again.
     *
     * @param theKey the key in question
     * @return whether the key has been released
     */
    public boolean isKeyUp(final KeyType theKey) {
        final int keyCode = enumToInt(theKey);

        final boolean r = myKeyUps[keyCode];
        myKeyUps[keyCode] = false;
        return r;
    }

    /**
     * Determines if a KeyType has pressed for the first time. This would only return
     * true the moment when the key has been pressed. Will return false for subsequent
     * calls until the key is released, and then pressed again.
     *
     * @param theKey the key in question
     * @return whether the key has been pressed
     */
    public boolean isKeyDown(final KeyType theKey) {
        final int keyCode = enumToInt(theKey);

        if (myKeyDowns[keyCode]) return false;

        myKeyDowns[keyCode] = myKeyHelds.contains(keyCode);
        return myKeyDowns[keyCode];
    }


    @Override
    public void keyPressed(final KeyEvent theE) {
        myKeyHelds.add(theE.getKeyCode());
        myKeyUps[theE.getKeyCode()] = false;
    }

    @Override
    public void keyReleased(final KeyEvent theE) {
        myKeyHelds.remove(theE.getKeyCode());
        myKeyDowns[theE.getKeyCode()] = false;
        myKeyUps[theE.getKeyCode()] = true;
    }
}
