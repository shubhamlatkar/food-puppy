package com.foodgrid.common.unit;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = {UserAuthEventDTO.class, AuthenticationEvent.class})
@AutoConfigureWebTestClient
class AuthenticationEventTest {

    @Test
    void testAuthenticationEventTest() {
        var eventUser = new UserAuthEventDTO(UserTypes.USER, "1", "test_username", "test_pass", UserActivities.LOGIN, "test_token", "USER", "1234567890", "testemail@email.com");
        var event = new AuthenticationEvent(true, List.of(eventUser));
        Assertions.assertNotNull(event.getIsUpdated());
        Assertions.assertNotNull(event.getUsers());
        Assertions.assertNotNull(event.toString());
        var users = event.getUsers();
        users.forEach(userAuthEventDTO -> {
            Assertions.assertNotNull(userAuthEventDTO.getUserId());
            Assertions.assertNotNull(userAuthEventDTO.getActivity());
            Assertions.assertNotNull(userAuthEventDTO.getPassword());
            Assertions.assertNotNull(userAuthEventDTO.getPhone());
            Assertions.assertNotNull(userAuthEventDTO.getRole());
            Assertions.assertNotNull(userAuthEventDTO.getToken());
            Assertions.assertNotNull(userAuthEventDTO.getUserType());
            Assertions.assertNotNull(userAuthEventDTO.getUsername());
            Assertions.assertNotNull(userAuthEventDTO.getEmail());
        });
    }
}
