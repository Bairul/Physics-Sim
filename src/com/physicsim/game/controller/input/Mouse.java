package com.physicsim.game.controller.input;

import com.physicsim.game.utility.Vector2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class for mouse input.
 *
 * @author Bairu Li
 */
public class Mouse extends MouseAdapter {
    private final Vector2 myOrigin;
    /** The X and Y coordinate of the mouse. */
    private final Vector2 myPosition;
    /** The button on the mouse that was pressed. */
    private int myButtonDown;
    /** Whether the left mouse button is lifted for manually getting mouse clicks. */
    private boolean isLeftLifted;

    /**
     * Constructor for mouse.
     */
    public Mouse(final Vector2 theOrigin) {
        myPosition = new Vector2();
        myOrigin = new Vector2(theOrigin);
        myButtonDown = -1;
        isLeftLifted = false;
    }

    /**
     * Returns the mouse position.
     * @return the position in a vector
     */
    public Vector2 getPos() {
        return myPosition;
    }

    /**
     * Returns what mouse button is being used.
     * @return whether the mouse click type is pressed down
     */
    public boolean isButtonDown(final ClickType theType) {
        return switch (theType) {
            case LeftClick -> myButtonDown == MouseEvent.BUTTON1;
            case RightClick -> myButtonDown == MouseEvent.BUTTON2;
            case MiddleClick -> myButtonDown == MouseEvent.BUTTON3;
        };
    }

    /**
     * Determines whether the left click is lifted, signifying that the end of a left click has started.
     * @return true if left click is lifted, and false otherwise
     */
    public boolean isLeftLifted() {
        boolean r = isLeftLifted;
        isLeftLifted = false;
        return r;
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
        myButtonDown = theE.getButton();
    }

    @Override
    public void mouseReleased(final MouseEvent theE) {
        myButtonDown = -1;
        if (theE.getButton() == MouseEvent.BUTTON1)
            isLeftLifted = true;
    }
}
