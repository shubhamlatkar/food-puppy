package com.foodgrid.common.unit;

import com.foodgrid.common.payload.dto.response.GenericIdResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureWebTestClient
class GenericIdResponseTests {
    @Test
    void testGenericIdResponseTests() {
        var res = new GenericIdResponse("1", "Test response");
        Assertions.assertNotNull(res.getMsg());
    }
}
