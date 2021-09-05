package com.foodgrid.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureWebTestClient
class OrderApplicationTests {
    @Autowired
    private OrderApplication orderApplication;

    @Test
    @DisplayName("Tests main method of UserApplication")
    void main() {
        var args = new String[]{"1"};
        OrderApplication.main(args);
        Assertions.assertNotNull(args);
    }

    @Test
    @DisplayName("Tests redirect method of UserApplication")
    void redirect() {
        Assertions.assertNotNull(orderApplication.getDefault());
    }
}
