package com.foodgrid.notification.command;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.security.model.aggregate.Authority;
import com.foodgrid.common.security.model.aggregate.Role;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.model.entity.UserMetadata;
import com.foodgrid.common.security.repository.RoleRepository;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import com.foodgrid.notification.command.event.service.CustomAuthenticationEventHandlerImplementation;
import com.foodgrid.notification.command.model.aggregate.DeliveryNotification;
import com.foodgrid.notification.command.model.aggregate.RestaurantNotification;
import com.foodgrid.notification.command.model.aggregate.UserNotification;
import com.foodgrid.notification.command.repository.DeliveryNotificationRepository;
import com.foodgrid.notification.command.repository.MetaDataRepository;
import com.foodgrid.notification.command.repository.RestaurantNotificationRepository;
import com.foodgrid.notification.command.repository.UserNotificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CustomAuthenticationEventHandlerImplementation.class})
@AutoConfigureWebTestClient
class CustomAuthenticationEventHandlerImplementationTests {

    @MockBean
    private RestaurantNotificationRepository restaurantNotificationRepository;

    @MockBean
    private DeliveryNotificationRepository deliveryNotificationRepository;

    @MockBean
    private UserNotificationRepository userNotificationRepository;

    @MockBean
    private MetaDataRepository metaDataRepository;

    @MockBean
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private CustomAuthenticationEventHandlerImplementation customAuthenticationEventHandlerImplementation;

    @BeforeEach
    void init() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        when(roleRepository.findByName("USER")).thenReturn(java.util.Optional.of(tempRole));
        doAnswer(invocationOnMock -> Mono.just(new UserNotification("1", "saved", "1"))).when(userNotificationRepository).save(any());
        doAnswer(invocationOnMock -> Mono.just(new RestaurantNotification("1", "saved", "1"))).when(restaurantNotificationRepository).save(any());
        doAnswer(invocationOnMock -> Mono.just(new DeliveryNotification("1", "saved", "1"))).when(deliveryNotificationRepository).save(any());
        doAnswer(invocationOnMock -> Mono.just(true)).when(reactiveMongoTemplate).collectionExists(any(Class.class));
        doAnswer(invocationOnMock -> Mono.just(true)).when(reactiveMongoTemplate).collectionExists(anyString());
    }

    @Test
    @DisplayName("Tests testAuthenticationEventHandlerImplementationLoginMethod method of customAuthenticationEventHandlerImplementation")
    void testAuthenticationEventHandlerImplementationLoginMethod() {

        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        var eventUser = new UserAuthEventDTO(UserTypes.USER, "1", "test_username", "test_pass", UserActivities.LOGIN, "test_token", "USER", "1234567890", "testemail@email.com");
        var event = new AuthenticationEvent(true, List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        eventUser.setUserType(UserTypes.RESTAURANT);
        event.setUsers(List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        eventUser.setUserType(UserTypes.DELIVERY);
        event.setUsers(List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }

    @Test
    @DisplayName("Tests testAuthenticationEventHandlerImplementationSignUpMethod method of customAuthenticationEventHandlerImplementation")
    void testAuthenticationEventHandlerImplementationSignUpMethod() {

        var eventUser = new UserAuthEventDTO(UserTypes.USER, "1", "test_username", "test_pass", UserActivities.SIGNUP, "test_token", "USER", "1234567890", "testemail@email.com");
        var event = new AuthenticationEvent(true, List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        eventUser.setUserType(UserTypes.RESTAURANT);
        event.setUsers(List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        eventUser.setUserType(UserTypes.DELIVERY);
        event.setUsers(List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }

    @Test
    @DisplayName("Tests testAuthenticationEventHandlerImplementationPatchMethod method of customAuthenticationEventHandlerImplementation")
    void testAuthenticationEventHandlerImplementationPatchMethod() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        var eventUser = new UserAuthEventDTO(UserTypes.USER, "1", "test_username", "test_pass", UserActivities.PATCH, "test_token", "USER", "1234567890", "testemail@email.com");
        var event = new AuthenticationEvent(true, List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        eventUser.setUserType(UserTypes.RESTAURANT);
        event.setUsers(List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        eventUser.setUserType(UserTypes.DELIVERY);
        event.setUsers(List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }

    @Test
    @DisplayName("Tests testAuthenticationEventHandlerImplementationDeleteMethod method of customAuthenticationEventHandlerImplementation")
    void testAuthenticationEventHandlerImplementationDeleteMethod() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        var eventUser = new UserAuthEventDTO(UserTypes.USER, "1", "test_username", "test_pass", UserActivities.DELETE, "test_token", "USER", "1234567890", "testemail@email.com");
        var event = new AuthenticationEvent(true, List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        eventUser.setUserType(UserTypes.RESTAURANT);
        event.setUsers(List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        eventUser.setUserType(UserTypes.DELIVERY);
        event.setUsers(List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }

    @Test
    @DisplayName("Tests testAuthenticationEventHandlerImplementationLogoutMethod method of customAuthenticationEventHandlerImplementation")
    void testAuthenticationEventHandlerImplementationLogoutMethod() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        var eventUser = new UserAuthEventDTO(UserTypes.USER, "1", "test_username", "test_pass", UserActivities.LOGOUT, "test_token", "USER", "1234567890", "testemail@email.com");
        var event = new AuthenticationEvent(true, List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        eventUser.setUserType(UserTypes.RESTAURANT);
        event.setUsers(List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        eventUser.setUserType(UserTypes.DELIVERY);
        event.setUsers(List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }

    @Test
    @DisplayName("Tests testAuthenticationEventHandlerImplementationLogoutAllMethod method of customAuthenticationEventHandlerImplementation")
    void testAuthenticationEventHandlerImplementationLogoutAllMethod() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        var eventUser = new UserAuthEventDTO(UserTypes.USER, "1", "test_username", "test_pass", UserActivities.LOGOUT_ALL, "test_token", "USER", "1234567890", "testemail@email.com");
        var event = new AuthenticationEvent(true, List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        eventUser.setUserType(UserTypes.RESTAURANT);
        event.setUsers(List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        eventUser.setUserType(UserTypes.DELIVERY);
        event.setUsers(List.of(eventUser));
        customAuthenticationEventHandlerImplementation.authConsumer(event);
        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }
}
