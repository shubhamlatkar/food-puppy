package com.foodgrid.accounts.command.event.handler;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.event.service.AuthenticationEventHandlerImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventHandler {

    private final AuthenticationEventHandlerImplementation authenticationEventHandlerImplementation;

    @Autowired
    public AuthenticationEventHandler(AuthenticationEventHandlerImplementation authenticationEventHandlerImplementation) {
        this.authenticationEventHandlerImplementation = authenticationEventHandlerImplementation;
    }

    @JmsListener(destination = "AUTHENTICATION")
    public void authConsumer(AuthenticationEvent event) {
        authenticationEventHandlerImplementation.authConsumer(event);
    }
}
