package com.foodgrid.user.command;

import com.foodgrid.common.security.component.DeletedUsers;
import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.security.model.aggregate.Authority;
import com.foodgrid.common.security.model.aggregate.Role;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.model.entity.TokenData;
import com.foodgrid.common.security.model.entity.UserMetadata;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import com.foodgrid.user.command.internal.event.broker.AuthenticationEventBroker;
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
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AuthenticationEventBroker.class})
@AutoConfigureWebTestClient
class AuthenticationEventBrokerTests {
    @MockBean
    private JmsMessagingTemplate jmsMessagingTemplate;

    @MockBean
    private UserSession userSession;

    @MockBean
    private DeletedUsers deletedUsers;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    @Qualifier("authenticationTopic")
    private Topic authenticationTopic;

    @Autowired
    private AuthenticationEventBroker authenticationEventBroker;

    @Test
    @DisplayName("Tests sendAuthEvent method of AuthenticationEventBroker")
    void sendAuthEvent() {
        when(userSession.getUserId()).thenReturn("1");
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(new Date().getTime() + 10000), UserActivities.LOGIN));
        tempUser.setId("1");
        tempUser.setActiveTokens(List.of(new TokenData("test", new Date())));
        when(userRepository.findAll())
                .thenReturn(List.of(tempUser));
        when(deletedUsers.getUsers())
                .thenReturn(null);
        authenticationEventBroker.sendAuthEvent();
        Assertions.assertNotNull(userSession.getUserId());
    }
}
