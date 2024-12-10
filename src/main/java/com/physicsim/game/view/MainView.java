package com.physicsim.game.view;

import com.physicsim.game.controller.VaadinGameRoot;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends Div {
    private final VaadinGameRoot gameRoot;

    public MainView() { // Injected via constructor
        setWidth("1280px");
        setHeight("720px");

        gameRoot = new VaadinGameRoot(this);
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
