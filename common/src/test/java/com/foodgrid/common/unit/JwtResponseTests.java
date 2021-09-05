package com.foodgrid.common.unit;

import com.foodgrid.common.payload.dto.response.JwtResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {JwtResponse.class})
@AutoConfigureWebTestClient
class JwtResponseTests {

    @Test
    void testJwtResponseTests() {
        var response = new JwtResponse("test_token", "Test_username", "1");
        Assertions.assertNotNull(response.toString());
        Assertions.assertNotNull(response.getId());
        Assertions.assertNotNull(response.getToken());
        Assertions.assertNotNull(response.getUsername());
    }
}
