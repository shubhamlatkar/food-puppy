package com.foodgrid.restaurant.command.internal.event.outbound;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.restaurant.shared.payload.MenuEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
@Slf4j
public class MenuEventBroker {

    @Autowired
    @Qualifier("menuTopic")
    private Topic menuTopic;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMenuEvent(MenuEventDTO menu) {
        try {
            log.info("Menu event: {}", menu);
            this.jmsMessagingTemplate.convertAndSend(menuTopic, menu);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
