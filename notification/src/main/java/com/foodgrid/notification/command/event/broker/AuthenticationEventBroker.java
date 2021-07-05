package com.foodgrid.notification.command.event.broker;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.notification.command.event.service.AuthenticationEventHandlerImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class AuthenticationEventBroker {

    @Autowired
    private AuthenticationEventHandlerImplementation authenticationEventHandlerImplementation;
    
    @Bean
    public Consumer<AuthenticationEvent> authentication() {
        return authenticationEvent -> authenticationEventHandlerImplementation.authConsumer(authenticationEvent);
    }
}
