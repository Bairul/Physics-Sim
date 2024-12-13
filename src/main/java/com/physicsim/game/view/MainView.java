package com.physicsim.game.view;

import com.physicsim.game.controller.VaadinGameRoot;
import com.physicsim.game.utility.Vector2;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {
    /** The scale of the canvas. */
    private static final int SCALE = 80;
    private final VaadinGameRoot gameRoot;

    public MainView() {
        // creates the game canvas and set the aspect ratio, default is 16:9
        final Vector2 vector = new Vector2(16, 9);
        vector.mul(SCALE);

        setWidth(vector.intX() + "px");
        setHeight(vector.intY() + "px");

        final DrawCanvas canvas = new DrawCanvas(vector.intX(), vector.intY());
        add(canvas);

        gameRoot = new VaadinGameRoot(canvas);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        gameRoot.start();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        gameRoot.stop();
    }
}
