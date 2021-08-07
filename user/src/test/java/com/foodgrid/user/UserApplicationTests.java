package com.foodgrid.user;

import com.foodgrid.user.command.internal.event.broker.AddressEventBroker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

@WebMvcTest(AddressEventBroker.class)
class UserApplicationTests {

    @Test
    void contextLoads() {
        String expect = "test";
        String actual = "test";
        Assertions.assertEquals(expect, actual);
    }

}
