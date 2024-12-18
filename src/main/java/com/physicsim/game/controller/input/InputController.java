package com.physicsim.game.controller.input;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.view.DrawCanvas;

/**
 * Class for all the inputs like mouse and keyboard.
 *
 * @author Bairu Li
 */
public class InputController {
    private final Mouse mouse;

    /**
     * Creates the controller. Adds the mouse and keyboard to the Game Canvas to make it functional.
     * Takes in a vector describing the origin of the canvas for the mouse.
     * <br><br>
     * By default, the mouse origin is in the top-left corner of the screen,
     * but it can be changed to be at the center of the screen.
     * @param theOrigin the origin
     */
    public InputController(final DrawCanvas canvas, final Vector2 theOrigin) {
        mouse = new Mouse(new Vector2(canvas.getCanvas().getBoundingClientRect().getLeft(), canvas.getCanvas().getBoundingClientRect().getTop()), theOrigin);
        mouse.addListenersCanvas(canvas);
    }

    /**
     * Gets the mouse input.
     * @return the mouse
     */
    public Mouse getMouse() {
        return mouse;
    }

    /**
     * Gets the mouse position as a vector. This is a method because getting the position of
     * the mouse is very common.
     * @return the mouse's x and y coordinates
     */
    public Vector2 getMousePos() {
        return mouse.getPos();
    }

//    /**
//     * Gets the keyboard input.
//     * @return the keyboard
//     */
//    public Keyboard getKeyboard() {
//        return myKeyboard;
//    }
}