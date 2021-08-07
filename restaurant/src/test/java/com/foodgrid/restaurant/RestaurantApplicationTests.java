package com.foodgrid.restaurant;

import com.foodgrid.restaurant.command.internal.event.outbound.AuthenticationEventBroker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(AuthenticationEventBroker.class)
class RestaurantApplicationTests {

    @Test
    void contextLoads() {
        String expect = "test";
        String actual = "test";
        Assertions.assertEquals(expect, actual);
    }
}
