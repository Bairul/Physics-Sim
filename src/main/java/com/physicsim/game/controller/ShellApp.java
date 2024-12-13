package com.physicsim.game.controller;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.shared.ui.Transport;
import org.springframework.stereotype.Component;

/**
 * Class for auto pushing updates from server to the client? Without it nothing moves.
 */
@Component
@Push(value = PushMode.AUTOMATIC, transport = Transport.WEBSOCKET_XHR)
public class ShellApp implements AppShellConfigurator {

}
