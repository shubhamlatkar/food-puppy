package com.foodgrid.common.unit;

import com.foodgrid.common.payload.dto.response.AuthenticationActionResponse;
import com.foodgrid.common.utility.UserActivities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest(classes = {AuthenticationActionResponse.class})
@AutoConfigureWebTestClient
class AuthenticationActionResponseTests {

    @Test
    void testAuthenticationActionResponseTests() {
        var response = new AuthenticationActionResponse(UserActivities.LOGIN, true, new Date(), "Test response");
        Assertions.assertNotNull(response.getActivity());
        Assertions.assertNotNull(response.getIsSuccessful());
        Assertions.assertNotNull(response.getMessage());
    }
}
