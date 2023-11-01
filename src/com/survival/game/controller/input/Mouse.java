package com.survival.game.controller.input;

import com.survival.game.utility.Vector2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Class for mouse input.
 */
public class Mouse implements MouseListener, MouseMotionListener {
    private final Vector2 myOrigin;
    /** The X and Y coordinate of the mouse. */
    private final Vector2 myPosition;
    /** The button on the mouse that was pressed. */
    private int myButton;
    /** Whether the left mouse button is lifted. */
    private boolean isLeftLifted;

    /**
     * Constructor for mouse.
     */
    public Mouse(final Vector2 theOrigin) {
        myPosition = new Vector2();
        myOrigin = new Vector2(theOrigin);
        myButton = -1;
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
     * @return the click type
     */
    public ClickType getButton() {
        return switch (myButton) {
            case 1 -> ClickType.LeftClick;
            case 3 -> ClickType.RightClick;
            default -> ClickType.Unknown;
        };
    }

    /**
     * Determines whether the left click is lifted, signifying that the left click has ended.
     * @return true if left click is lifted, and false otherwise
     */
    public boolean isLeftLifted() {
        return isLeftLifted;
    }

    public void offLeftLifted() {
        isLeftLifted = false;
    }

    // mouse motion listener implemented methods
    /**
     * Sets the position of the mouse when the mouse button is held down and then moved.
     * @param theE the event to be processed
     */
    @Override
    public void mouseDragged(final MouseEvent theE) {
        myPosition.set(theE.getX() - myOrigin.getX(), theE.getY() - myOrigin.getY());
    }

    /**
     * Sets the position of the mouse when the mouse when no button is held down and is moving.
     * @param theE the event to be processed
     */
    @Override
    public void mouseMoved(final MouseEvent theE) {
        myPosition.set(theE.getX() - myOrigin.getX(), theE.getY() - myOrigin.getY());
    }

    // mouse listener implemented methods
    /**
     * Determines what mouse button was held down.
     * @param theE the event to be processed
     */
    @Override
    public void mousePressed(final MouseEvent theE) {
        myButton = theE.getButton();
    }

    /**
     * Determines what mouse button was lifted.
     * @param theE the event to be processed
     */
    @Override
    public void mouseReleased(final MouseEvent theE) {
        myButton = -1;
        if (theE.getButton() == MouseEvent.BUTTON1)
            isLeftLifted = true;
    }

    // unused implemented methods
    @Override
    public void mouseClicked(final MouseEvent theE) {

    }

    @Override
    public void mouseEntered(final MouseEvent theE) {

    }

    @Override
    public void mouseExited(final MouseEvent theE) {

    }
}
