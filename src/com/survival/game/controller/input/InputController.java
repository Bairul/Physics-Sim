package com.survival.game.controller.input;

import com.survival.game.view.GameCanvas;

public class InputController {
    private Mouse myMouse;
    public InputController(final GameCanvas theCanvas) {
        myMouse = new Mouse();
        theCanvas.getCanvas().addMouseListener(myMouse);
        theCanvas.getCanvas().addMouseMotionListener(myMouse);
        theCanvas.getFrame().addMouseListener(myMouse);
        theCanvas.getFrame().addMouseMotionListener(myMouse);
    }

    public Mouse getMouse() {
        return myMouse;
    }
}
