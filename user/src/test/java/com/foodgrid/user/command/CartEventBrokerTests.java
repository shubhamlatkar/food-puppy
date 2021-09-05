package com.foodgrid.user.command;

import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.user.command.internal.event.broker.CartEventBroker;
import com.foodgrid.user.command.internal.model.entity.ItemCommandModel;
import com.foodgrid.user.shared.payload.CartEventDTO;
import com.foodgrid.user.shared.utility.CartActivities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Topic;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CartEventBroker.class})
@AutoConfigureWebTestClient
class CartEventBrokerTests {
    @MockBean
    private JmsMessagingTemplate jmsMessagingTemplate;

    @MockBean
    private UserSession userSession;

    @MockBean
    @Qualifier("cartTopic")
    private Topic cartTopic;

    @Autowired
    private CartEventBroker cartEventBroker;

    @Test
    @DisplayName("Tests sendCartEvent method of CartEventBroker")
    void sendCartEvent() {
        when(userSession.getUserId()).thenReturn("1");
        var cart = new CartEventDTO(CartActivities.ADD_ITEM, new ItemCommandModel("1", "test", 12.23, 1), "1", "1");
        cartEventBroker.sendCartEvent(cart);
        Assertions.assertNotNull(userSession.getUserId());
    }
}
