package com.foodgrid.eureka;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureWebTestClient
class EurekaApplicationTests {

    @Test
    @DisplayName("Tests main method of eurekaApplication")
    void main() {
        var args = new String[]{"1"};
        EurekaApplication.main(args);
        Assertions.assertNotNull(args);
    }

}
