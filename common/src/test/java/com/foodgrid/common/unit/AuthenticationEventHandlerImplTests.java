package com.foodgrid.common.unit;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.event.service.AuthenticationEventHandlerImplementation;
import com.foodgrid.common.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.model.aggregate.Authority;
import com.foodgrid.common.security.model.aggregate.Role;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.model.entity.UserMetadata;
import com.foodgrid.common.security.repository.RoleRepository;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class AuthenticationEventHandlerImplTests {

    @Autowired
    private AuthenticationEventHandlerImplementation authenticationEventHandlerImplementation;

    @MockBean
    private UserDetailsServiceImplementation userDetailsServiceImplementation;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    void testAuthenticationEventHandlerImplementationLoginMethod() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        when(roleRepository.findByName("USER")).thenReturn(java.util.Optional.of(tempRole));

        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        when(userRepository.findByUsername("test_username"))
                .thenReturn(java.util.Optional.of(tempUser));

        var eventUser = new UserAuthEventDTO(UserTypes.USER, "1", "test_username", "test_pass", UserActivities.LOGIN, "test_token", "USER", "1234567890", "testemail@email.com");
        var event = new AuthenticationEvent(true, List.of(eventUser));
        authenticationEventHandlerImplementation.authConsumer(event);
        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }

}
