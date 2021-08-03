package com.foodgrid.common.unit;

import com.foodgrid.common.event.service.AuthenticationEventHandlerImplementation;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@DataMongoTest
class AuthenticationEventHandlerImplTests {

    @Autowired
    private AuthenticationEventHandlerImplementation authenticationEventHandlerImplementation;

    @MockBean
    private UserDetailsServiceImplementation userDetailsServiceImplementation;

    @MockBean
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        String expect = "test";
        String actual = "test";
        Assertions.assertEquals(expect, actual);
    }

}
