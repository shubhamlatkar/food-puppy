package com.foodgrid.common.unit;

import com.foodgrid.common.security.implementation.UserDetailsImplementation;
import com.foodgrid.common.security.model.aggregate.Authority;
import com.foodgrid.common.security.model.aggregate.Role;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.model.entity.UserMetadata;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = {User.class, Role.class})
@AutoConfigureWebTestClient
class UserDetailsImplementationTests {

    @Test
    void testUserDetailsImplementationTestsBuild() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        var user = UserDetailsImplementation.build(tempUser);
        Assertions.assertNotNull(user.toString());
        Assertions.assertNotNull(user.getEmail());
        Assertions.assertNotNull(user.getAuthorities());
        Assertions.assertNotNull(user.getPassword());
        Assertions.assertTrue(user.isAccountNonLocked());
        Assertions.assertTrue(user.isCredentialsNonExpired());
        Assertions.assertTrue(user.isEnabled());
    }
}
