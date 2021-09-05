package com.foodgrid.common.unit;

import com.foodgrid.common.payload.dto.request.LogIn;
import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.utility.UserTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest(classes = {LogIn.class, SignUp.class})
@AutoConfigureWebTestClient
class RequestModelTests {

    @Test
    void testLogIn() {
        var login = new LogIn("test_username", "test_pass");
        Assertions.assertNotNull(login.toString());
        Assertions.assertNotNull(login.getPassword());
        Assertions.assertNotNull(login.getUsername());
    }

    @Test
    void testSignUp() {
        var signUp = new SignUp(
                "rosewood",
                "rosewood@shu.com",
                Set.of("ROLE_USER"),
                "rosewood@shu.com",
                "1234567890",
                UserTypes.USER
        );
        Assertions.assertNotNull(signUp.toString());
        Assertions.assertNotNull(signUp.getPassword());
        Assertions.assertNotNull(signUp.getUsername());
        Assertions.assertNotNull(signUp.getPhone());
        Assertions.assertNotNull(signUp.getRoles());
        Assertions.assertNotNull(signUp.getType());
    }


}
