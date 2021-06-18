package com.foodgrid.restaurant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestaurantApplicationTests {

    @Test
    void contextLoads() {
        String expect = "test";
        String actual = "test";
        Assertions.assertEquals(expect, actual);
    }
}
