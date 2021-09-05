package com.foodgrid.common.unit;

import com.foodgrid.common.payload.dto.response.GenericMessageResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {GenericMessageResponse.class})
@AutoConfigureWebTestClient
class GenericMessageResponseTests {

    @Test
    void testGenericMessageResponseTests() {
        var response = new GenericMessageResponse("Test response");
        Assertions.assertNotNull(response.getMessage());
    }
}
