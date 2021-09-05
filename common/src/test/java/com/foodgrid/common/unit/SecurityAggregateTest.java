package com.foodgrid.common.unit;

import com.foodgrid.common.security.model.aggregate.Authority;
import com.foodgrid.common.security.model.aggregate.Role;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.model.entity.TokenData;
import com.foodgrid.common.security.model.entity.UserMetadata;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = {Authority.class, Role.class, User.class, TokenData.class, UserMetadata.class})
@AutoConfigureWebTestClient
class SecurityAggregateTest {

    @Test
    void testAuthority() {
        var authority = new Authority("1", "test_authority");
        var namedAuthority = new Authority("test-name");
        namedAuthority.setId("2");
        Assertions.assertNotNull(authority.getName());
        Assertions.assertNotNull(authority.getId());
        Assertions.assertNotNull(namedAuthority.getName());
        Assertions.assertNotNull(namedAuthority.getId());
        Assertions.assertNotNull(namedAuthority.toString());

    }

    @Test
    void testRole() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        tempRole.setId("1");
        Assertions.assertNotNull(tempRole.getName());
        Assertions.assertNotNull(tempRole.getId());
        Assertions.assertNotNull(tempRole.toString());
        Assertions.assertNotNull(tempRole.getAuthorities());

    }


    @Test
    void testUser() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempRoleTest = new Role("TEST", List.of(new Authority("1", "TEST_AUTH")));
        var list = new ArrayList<Role>();
        list.add(tempRole);
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", list, UserTypes.USER);
        var plainUser = new User();
        plainUser.setId("2");
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        tempUser.addToken("TEST_TOKEN");
        tempUser.removeToken("TEST_TOKEN");
        tempUser.deleteActivity();
        tempUser.addRole(tempRoleTest);
        tempUser.setEmail("test@test.com");
        tempUser.setPhone("0987654321");
        tempUser.setType(UserTypes.USER);
        Assertions.assertNotNull(tempUser.getUsername());
        Assertions.assertNotNull(tempUser.getId());
        Assertions.assertNotNull(tempUser.toString());
        Assertions.assertNotNull(tempUser.getRoles());
        Assertions.assertNotNull(tempUser.getPhone());
        Assertions.assertNotNull(tempUser.getActiveTokens());
        Assertions.assertNotNull(plainUser.getId());
    }

    @Test
    void testTokenData() {
        var token = new TokenData("test_token", new Date());
        Assertions.assertNotNull(token.getToken());
        Assertions.assertNotNull(token.toString());
        Assertions.assertNotNull(token.getCreatedAt());
    }

    @Test
    void testUserMetadata() {
        var data = new UserMetadata();
        data.setCreatedAt(new Date());
        data.setLastActivity(UserActivities.LOGIN);
        data.setLastDeletedToken("test_token");
        data.setLastUpdatedAt(new Date());
        Assertions.assertNotNull(data.toString());
        Assertions.assertNotNull(data.getCreatedAt());
        Assertions.assertNotNull(data.getLastActivity());
        Assertions.assertNotNull(data.getLastDeletedToken());
        Assertions.assertNotNull(data.getLastUpdatedAt());
    }
}
