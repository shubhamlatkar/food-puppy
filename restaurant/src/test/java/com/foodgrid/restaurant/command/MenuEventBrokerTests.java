package com.foodgrid.restaurant.command;

import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.restaurant.command.internal.event.outbound.MenuEventBroker;
import com.foodgrid.restaurant.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.restaurant.shared.payload.MenuEventDTO;
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

@SpringBootTest(classes = {MenuEventBroker.class})
@AutoConfigureWebTestClient
class MenuEventBrokerTests {

    @MockBean
    private JmsMessagingTemplate jmsMessagingTemplate;
    @MockBean
    private UserSession userSession;
    @MockBean
    @Qualifier("menuTopic")
    private Topic menuTopic;

    @Autowired
    private MenuEventBroker menuEventBroker;

    @Test
    @DisplayName("Tests sendMenuEvent method of MenuEventBroker")
    void sendMenuEvent() {
        when(userSession.getUserId()).thenReturn("1");
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        var menu = new MenuEventDTO(itemRequest, "1", "1", "1", 3.2f, CrudActions.ADD);
        menuEventBroker.sendMenuEvent(menu);
        Assertions.assertNotNull(userSession.getUserId());
    }
}
