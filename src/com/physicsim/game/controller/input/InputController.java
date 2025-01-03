package com.physicsim.game.controller.input;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.view.GameCanvas;

/**
 * Class for all the inputs like mouse and keyboard.
 *
 * @author Bairu Li
 */
public class InputController {
    /** Input from the mouse. */
    private final Mouse myMouse;
    /** Input from the keyboard. */
    private final Keyboard myKeyboard;

    /**
     * Creates the controller. Adds the mouse and keyboard to the Game Canvas to make it functional.
     * Takes in a vector describing the origin of the canvas for the mouse.
     * <br><br>
     * By default, the mouse origin is in the top-left corner of the screen,
     * but it can be changed to be at the center of the screen.
     * @param theCanvas the game canvas
     * @param theOrigin the origin
     */
    public InputController(final GameCanvas theCanvas, final Vector2 theOrigin) {
        myMouse = new Mouse(theOrigin);
        theCanvas.getCanvas().addMouseListener(myMouse);
        theCanvas.getCanvas().addMouseMotionListener(myMouse);
        theCanvas.getFrame().addMouseListener(myMouse);
        theCanvas.getFrame().addMouseMotionListener(myMouse);

        myKeyboard = new Keyboard();
        theCanvas.getCanvas().addKeyListener(myKeyboard);
        theCanvas.getFrame().addKeyListener(myKeyboard);
    }

    /**
     * Gets the mouse input.
     * @return the mouse
     */
    public Mouse getMouse() {
        return myMouse;
    }

    /**
     * Gets the mouse position as a vector. This is a method because getting the position of
     * the mouse is very common.
     * @return the mouse's x and y coordinates
     */
    public Vector2 getMousePos() {
        return  myMouse.getPos();
    }

    /**
     * Gets the keyboard input.
     * @return the keyboard
     */
    public Keyboard getKeyboard() {
        return myKeyboard;
    }
}
