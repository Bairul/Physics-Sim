package com.physicsim.game.controller.input;

import com.physicsim.game.utility.Vector2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for mouse input.
 *
 * @author Bairu Li
 */
public class Mouse extends MouseAdapter {
    private final Vector2 myOrigin;
    /** The X and Y coordinate of the mouse. */
    private final Vector2 myPosition;
    /** Whether a mouse button is let go. */
    private final boolean[] myButtonUps;
    /** Whether a mouse button is pressed for the first time. */
    private final boolean[] myButtonDowns;
    /** Storing the state when a mouse button is being held down. */
    private final Set<Integer> myButtonHelds;


    /**
     * Constructor for mouse.
     */
    public Mouse(final Vector2 theOrigin) {
        myPosition = new Vector2();
        myOrigin = new Vector2(theOrigin);
        myButtonDowns = new boolean[8];
        myButtonUps = new boolean[8];
        myButtonHelds = new HashSet<>();
    }

    /**
     * Returns the mouse position.
     * @return the position in a vector
     */
    public Vector2 getPos() {
        return myPosition;
    }

    /**
     * Converts the ClickType enum to integers.
     * @param theClick the click type
     * @return the respective integer
     */
    private int enumToInt(final ClickType theClick) {
        return switch (theClick) {
            case LeftClick -> MouseEvent.BUTTON1;
            case RightClick -> MouseEvent.BUTTON2;
            case MiddleClick -> MouseEvent.BUTTON3;
            case SideButton1 -> 4;
            case SideButton2 -> 5;
        };
    }

    /**
     * Returns what mouse button is being used.
     * @return whether the mouse click type is pressed down
     */
    public boolean isButtonHeld(final ClickType theClick) {
        return myButtonHelds.contains(enumToInt(theClick));
    }

    /**
     * Determines whether the click has released for the first time. This would only return
     * true the moment the click has been released. Will return false for subsequent
     * calls until the click is pressed, and then released again.
     * @return true if left click is lifted
     */
    public boolean isButtonUp(final ClickType theClick) {
        final int button = enumToInt(theClick);

        final boolean r = myButtonUps[button];
        myButtonUps[button] = false;
        return r;
    }

    /**
     * Determines whether the click has pressed for the first time. This would only return
     * true the moment the click has been pressed. Will return false for subsequent
     * calls until the click is released, and then pressed again.
     * @return true if left click is lifted
     */
    public boolean isButtonDown(final ClickType theClick) {
        final int button = enumToInt(theClick);

        if (myButtonDowns[button]) return false;

        myButtonDowns[button] = myButtonHelds.contains(button);
        return myButtonDowns[button];
    }

    @Override
    public void mouseDragged(final MouseEvent theE) {
        myPosition.set(theE.getX() - myOrigin.getX(), theE.getY() - myOrigin.getY());
    }

    @Override
    public void mouseMoved(final MouseEvent theE) {
        myPosition.set(theE.getX() - myOrigin.getX(), theE.getY() - myOrigin.getY());
    }

    @Override
    public void mousePressed(final MouseEvent theE) {
        myButtonHelds.add(theE.getButton());
        myButtonUps[theE.getButton()] = false;
    }

    @Override
    public void mouseReleased(final MouseEvent theE) {
        myButtonHelds.remove(theE.getButton());
        myButtonDowns[theE.getButton()] = false;
        myButtonUps[theE.getButton()] = true;
    }
}
