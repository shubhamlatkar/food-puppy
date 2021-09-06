package com.foodgrid.delivery.command;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.event.service.AuthenticationEventHandlerImplementation;
import com.foodgrid.common.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.security.model.aggregate.Authority;
import com.foodgrid.common.security.model.aggregate.Role;
import com.foodgrid.common.security.repository.RoleRepository;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import com.foodgrid.delivery.command.external.event.handler.AuthenticationEventHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AuthenticationEventHandler.class})
@AutoConfigureWebTestClient
class AuthenticationEventHandlerTests {

    @Autowired
    private AuthenticationEventHandler authenticationEventHandler;

    @MockBean
    private AuthenticationEventHandlerImplementation authenticationEventHandlerImplementation;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    void testAuthenticationEventHandlerAuthConsumer() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        when(roleRepository.findByName("USER")).thenReturn(java.util.Optional.of(tempRole));

        doAnswer(invocation -> null).when(authenticationEventHandlerImplementation).authConsumer(any());

        authenticationEventHandler.authConsumer(new AuthenticationEvent(true, List.of(new UserAuthEventDTO(UserTypes.USER, "1", "test", "test", UserActivities.LOGIN, "token", "USER", "1234567890", "test@test.com"))));
        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }
}
