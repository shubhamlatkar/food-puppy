package com.foodgrid.restaurant.command;

import com.foodgrid.common.security.component.DeletedUsers;
import com.foodgrid.common.security.model.aggregate.Authority;
import com.foodgrid.common.security.model.aggregate.Role;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.model.entity.TokenData;
import com.foodgrid.common.security.model.entity.UserMetadata;
import com.foodgrid.common.security.repository.RoleRepository;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import com.foodgrid.restaurant.command.internal.event.outbound.AuthenticationEventBroker;
import org.junit.jupiter.api.Assertions;
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
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AuthenticationEventBroker.class})
@AutoConfigureWebTestClient
class AuthenticationEventBrokerTests {

    @Autowired
    private AuthenticationEventBroker authenticationEventBroker;

    @MockBean
    private JmsMessagingTemplate jmsMessagingTemplate;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    @Qualifier("authenticationTopic")
    private Topic authenticationTopic;

    @MockBean
    private DeletedUsers deletedUsers;

    @MockBean
    private UserRepository userRepository;


    @Test
    void testAuthenticationEventBrokerTestsSend() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        when(roleRepository.findByName("USER")).thenReturn(java.util.Optional.of(tempRole));

        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setActiveTokens(List.of(new TokenData("test", new Date())));
        tempUser.setId("1");
        when(userRepository.findAll())
                .thenReturn(List.of(tempUser));

        when(deletedUsers.getUsers())
                .thenReturn(Set.of(tempUser));


        authenticationEventBroker.send();

        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }
}
