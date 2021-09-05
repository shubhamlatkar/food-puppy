package com.foodgrid.common.unit;

import com.foodgrid.common.payload.dco.UserToUserAuthEvent;
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

@SpringBootTest(classes = {UserToUserAuthEvent.class, User.class,Role.class})
@AutoConfigureWebTestClient
class UserToUserAuthEventTests {

    @Test
    void testUserToUserAuthEventTestsLogin() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        tempUser.addToken("Test_token");
        var userToUserAuthEvent = new UserToUserAuthEvent(tempUser, UserTypes.USER);
        Assertions.assertNotNull(userToUserAuthEvent.getUser());
    }

    @Test
    void testUserToUserAuthEventTestsLogout() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGOUT));
        var metaData = tempUser.getMetadata();
        metaData.setLastDeletedToken("test_token");
        tempUser.setMetadata(metaData);
        tempUser.setId("1");
        tempUser.addToken("Test_token");
        var userToUserAuthEvent = new UserToUserAuthEvent(tempUser, UserTypes.USER);
        Assertions.assertNotNull(userToUserAuthEvent.getUser());
    }

    @Test
    void testUserToUserAuthEventTestsSignup() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.SIGNUP));
        var metaData = tempUser.getMetadata();
        metaData.setLastDeletedToken("test_token");
        tempUser.setMetadata(metaData);
        tempUser.setId("1");
        tempUser.addToken("Test_token");
        var userToUserAuthEvent = new UserToUserAuthEvent(tempUser, UserTypes.USER);
        Assertions.assertNotNull(userToUserAuthEvent.getUser());
    }
}
