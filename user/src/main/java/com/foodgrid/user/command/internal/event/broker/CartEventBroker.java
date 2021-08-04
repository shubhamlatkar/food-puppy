package com.foodgrid.user.command.internal.event.broker;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.user.shared.payload.CartEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
@Slf4j
public class CartEventBroker {

    @Autowired
    @Qualifier("cartTopic")
    private Topic cartTopic;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendCartEvent(CartEventDTO cart) {
        try {
            log.info("Cart event: {}", cart);
            this.jmsMessagingTemplate.convertAndSend(cartTopic, cart);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

}