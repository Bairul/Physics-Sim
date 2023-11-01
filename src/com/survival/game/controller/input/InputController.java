package com.survival.game.controller.input;

import com.survival.game.utility.Vector2;
import com.survival.game.view.GameCanvas;

/**
 * Class for all the inputs like mouse and keyboard.
 */
public class InputController {
    /** Input for the mouse. */
    private Mouse myMouse;

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
}
