package com.foodgrid.user.command.internal.event.broker;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.user.shared.payload.AddressEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
@Slf4j
public class AddressEventBroker {

    @Autowired
    @Qualifier("addressTopic")
    private Topic addressTopic;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendAddressEvent(AddressEventDto address) {
        try {
            log.info("Address event: {}", address);
            this.jmsMessagingTemplate.convertAndSend(addressTopic, address);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

}
