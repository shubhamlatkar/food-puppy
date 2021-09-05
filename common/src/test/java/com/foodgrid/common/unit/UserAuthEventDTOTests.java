package com.foodgrid.common.unit;

import com.foodgrid.common.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {UserAuthEventDTO.class})
@AutoConfigureWebTestClient
class UserAuthEventDTOTests {
    @Test
    void testUserAuthEventDTOTests() {
        var eventUser = new UserAuthEventDTO(UserTypes.USER, "1", "test_username", "test_pass", UserActivities.LOGIN, "test_token", "USER", "1234567890", "testemail@email.com");
        Assertions.assertNotNull(eventUser.getEmail());
        Assertions.assertNotNull(eventUser.getUserType());
        Assertions.assertNotNull(eventUser.getPhone());
        Assertions.assertNotNull(eventUser.getEmail());
        Assertions.assertNotNull(eventUser.getUserType());
        Assertions.assertNotNull(eventUser.getActivity());
    }

}
