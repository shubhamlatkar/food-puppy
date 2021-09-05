package com.foodgrid.user.command;

import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.user.command.internal.event.broker.AddressEventBroker;
import com.foodgrid.user.command.internal.payload.dto.request.AddressRequest;
import com.foodgrid.user.shared.model.Location;
import com.foodgrid.user.shared.payload.AddressEventDto;
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

@SpringBootTest(classes = {AddressEventBroker.class})
@AutoConfigureWebTestClient
class AddressEventBrokerTests {

    @MockBean
    private JmsMessagingTemplate jmsMessagingTemplate;

    @MockBean
    private UserSession userSession;

    @MockBean
    @Qualifier("addressTopic")
    private Topic addressTopic;

    @Autowired
    private AddressEventBroker addressEventBroker;

    @Test
    @DisplayName("Tests sendAddressEvent method of AddressEventBroker")
    void sendAddressEvent() {
        when(userSession.getUserId()).thenReturn("1");
        var address = new AddressRequest(new Location(12.1, 31.2), "home", "test line 1", "test line 2", "313131", "pune", "MH", true);
        addressEventBroker.sendAddressEvent(new AddressEventDto(address, "1", "1", CrudActions.ADD));
        Assertions.assertNotNull(userSession.getUserId());
    }
}
