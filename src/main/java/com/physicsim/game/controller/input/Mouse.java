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
    private final Vector2 myOffset;
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
    public Mouse(final Vector2 theOffset, final Vector2 theOrigin) {
        myPosition = new Vector2();
        myOffset = new Vector2(theOffset);
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
            case RightClick -> 2;
            case MiddleClick -> 1;
            case SideButton1 -> 3;
            case SideButton2 -> 4;
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

    // === event handling === \\

    /**
     * Handles mouse down event.
     * @param theEvent the mouse event
     */
    private void onMouseDown(final Event theEvent) {
        int button = getMouseButton(theEvent);

        myButtonHelds.add(button);
        myButtonUps[button] = false;
    }

    /**
     * Handles mouse up event.
     * @param theEvent the mouse event
     */
    private void onMouseUp(final Event theEvent) {
        int button = getMouseButton(theEvent);

        myButtonHelds.remove(button);
        myButtonDowns[button] = false;
        myButtonUps[button] = true;
    }

    /**
     * Handles mouse movement event.
     * @param theEvent the mouse event
     */
    private void onMouseMove(final Event theEvent) {
        double x = getClientX(theEvent) - myOffset.getX() - myOrigin.getX();
        double y = getClientY(theEvent) - myOffset.getY() - myOrigin.getY();

        myPosition.set(x, y);
    }

    // JavaScript interop
    @org.teavm.jso.JSBody(params = {"event"}, script = "return event.clientX;")
    private static native int getClientX(final Event event);

    @org.teavm.jso.JSBody(params = {"event"}, script = "return event.clientY;")
    private static native int getClientY(final Event event);

    @org.teavm.jso.JSBody(params = {"event"}, script = "return event.button;")
    private static native int getMouseButton(final Event event);

    /**
     * Adds mouse listener to the canvas.
     * @param theCanvas the canvas to get html canvas
     */
    public void addListenersCanvas(final DrawCanvas theCanvas) {
        theCanvas.getCanvas().addEventListener("mousedown", this::onMouseDown);
        theCanvas.getCanvas().addEventListener("mouseup", this::onMouseUp);
        theCanvas.getCanvas().addEventListener("mousemove", this::onMouseMove);
    }
}
