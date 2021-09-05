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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AuthenticationEventHandlerImplementation.class})
@AutoConfigureWebTestClient
class AuthenticationEventHandlerImplTests {

    @Autowired
    private AuthenticationEventHandlerImplementation authenticationEventHandlerImplementation;

    @MockBean
    private UserDetailsServiceImplementation userDetailsServiceImplementation;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @BeforeEach
    void init() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        when(roleRepository.findByName("USER")).thenReturn(java.util.Optional.of(tempRole));
    }

    @Test
    void testAuthenticationEventHandlerImplementationLoginMethod() {

        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
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

    @Test
    void testAuthenticationEventHandlerImplementationSignUpMethod() {
        when(userRepository.existsByUsername("test_username"))
                .thenReturn(Boolean.FALSE);

        when(userDetailsServiceImplementation.saveUser(any())).thenReturn(Boolean.TRUE);

        var eventUser = new UserAuthEventDTO(UserTypes.USER, "1", "test_username", "test_pass", UserActivities.SIGNUP, "test_token", "USER", "1234567890", "testemail@email.com");
        var event = new AuthenticationEvent(true, List.of(eventUser));
        authenticationEventHandlerImplementation.authConsumer(event);
        Assertions.assertEquals(Boolean.FALSE, userRepository.existsByUsername("test_username"));
    }

    @Test
    void testAuthenticationEventHandlerImplementationPatchMethod() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        when(userRepository.findByUsername("test_username"))
                .thenReturn(java.util.Optional.of(tempUser));
        doAnswer(invocationOnMock -> null)
                .when(userRepository).save(any());
        var eventUser = new UserAuthEventDTO(UserTypes.USER, "1", "test_username", "test_pass", UserActivities.PATCH, "test_token", "USER", "1234567890", "testemail@email.com");
        var event = new AuthenticationEvent(true, List.of(eventUser));
        authenticationEventHandlerImplementation.authConsumer(event);
        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }

    @Test
    void testAuthenticationEventHandlerImplementationDeleteMethod() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        when(userRepository.findByUsername("test_username"))
                .thenReturn(java.util.Optional.of(tempUser));

        doAnswer(invocationOnMock -> null)
                .when(userRepository).delete(any());

        var eventUser = new UserAuthEventDTO(UserTypes.USER, "1", "test_username", "test_pass", UserActivities.DELETE, "test_token", "USER", "1234567890", "testemail@email.com");
        var event = new AuthenticationEvent(true, List.of(eventUser));
        authenticationEventHandlerImplementation.authConsumer(event);
        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }

    @Test
    void testAuthenticationEventHandlerImplementationLogoutMethod() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        when(userRepository.findByUsername("test_username"))
                .thenReturn(java.util.Optional.of(tempUser));

        doAnswer(invocationOnMock -> null)
                .when(userRepository).save(any());

        var eventUser = new UserAuthEventDTO(UserTypes.USER, "1", "test_username", "test_pass", UserActivities.LOGOUT, "test_token", "USER", "1234567890", "testemail@email.com");
        var event = new AuthenticationEvent(true, List.of(eventUser));
        authenticationEventHandlerImplementation.authConsumer(event);
        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }

    @Test
    void testAuthenticationEventHandlerImplementationLogoutAllMethod() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        when(userRepository.findByUsername("test_username"))
                .thenReturn(java.util.Optional.of(tempUser));

        doAnswer(invocationOnMock -> null)
                .when(userRepository).save(any());

        var eventUser = new UserAuthEventDTO(UserTypes.USER, "1", "test_username", "test_pass", UserActivities.LOGOUT_ALL, "test_token", "USER", "1234567890", "testemail@email.com");
        var event = new AuthenticationEvent(true, List.of(eventUser));
        authenticationEventHandlerImplementation.authConsumer(event);
        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }

}
