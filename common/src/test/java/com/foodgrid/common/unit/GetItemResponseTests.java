package com.foodgrid.common.unit;


import com.foodgrid.common.payload.dto.response.GetItemResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {GetItemResponse.class})
@AutoConfigureWebTestClient
class GetItemResponseTests {

    @Test
    void testGetItemResponseTests() {
        var response = new GetItemResponse("1", "Test item", 123.12);
        Assertions.assertNotNull(response.toString());
        Assertions.assertNotNull(response.getId());
        Assertions.assertNotNull(response.getName());
    }
}
