package com.physicsim.game.controller.input;

import com.physicsim.game.utility.Vector2;
import com.physicsim.game.view.DrawCanvas;
import org.teavm.jso.dom.events.Event;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for mouse input.
 *
 * @author Bairu Li
 */
public class Mouse {
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
            case LeftClick -> 0;
            case RightClick -> 1;
            case MiddleClick -> 2;
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

    private void onMouseDown(Event event) {
        handleMouseEvent("Mouse Down", event);
    }

    private void handleMouseEvent(String action, Event event) {
        // Extract mouse properties using JavaScript interop
        int x = getClientX(event);
        int y = getClientY(event);
        System.out.println(action + " at: (" + x + ", " + y + ")");
    }

    // JavaScript interop to access `clientX`
    private native int getClientX(Event event) /*-{
        return event.clientX || 0;
    }-*/;

    // JavaScript interop to access `clientY`
    private native int getClientY(Event event) /*-{
        return event.clientY || 0;
    }-*/;

    public void addListenersCanvas(DrawCanvas canvas) {
        canvas.getCanvas().addEventListener("mousedown", this::onMouseDown);
//        canvas.getCanvas().addEventListener("mouseup", this::onMouseUp);
//        canvas.getCanvas().addEventListener("mousemove", this::onMouseMove);
//        canvas.getCanvas().addEventListener("click", this::onMouseClick);
    }
}
