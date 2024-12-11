package com.physicsim.game.view;

import com.physicsim.game.controller.VaadinGameRoot;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {
    private final VaadinGameRoot gameRoot;

    public MainView() {
        setWidth("1280px");
        setHeight("720px");

        DrawCanvas canvas = new DrawCanvas(1280, 720);
        add(canvas);

        // Draw some shapes
//        canvas.drawLine(50, 50, 200, 200, "black", 2);
//        canvas.drawRectangle(100, 100, 200, 100, "red", "transparent");
//        canvas.drawCircle(400, 300, 50, "blue", "yellow");
//        canvas.drawPolygon("300,100 400,200 500,100", "green", "lightgreen");

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
