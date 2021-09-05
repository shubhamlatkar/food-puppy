package com.foodgrid.gateway;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureWebTestClient
class GatewayApplicationTests {

    @Test
    @DisplayName("Tests main method of gateway")
    void main() {
        var args = new String[]{"1"};
        GatewayApplication.main(args);
        Assertions.assertNotNull(args);
    }
}
