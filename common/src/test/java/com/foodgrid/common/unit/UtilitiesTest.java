package com.foodgrid.common.unit;

import com.foodgrid.common.utility.Authorities;
import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.common.utility.UserTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import static com.foodgrid.common.utility.Authorities.*;
import static com.foodgrid.common.utility.CrudActions.*;
import static com.foodgrid.common.utility.Roles.*;

@SpringBootTest
@AutoConfigureWebTestClient
class UtilitiesTest {

    @Test
    void testAuthorities() {
        Assertions.assertEquals("service:read", SERVICE_READ.getValue());
        Assertions.assertEquals("service:write", SERVICE_WRITE.getValue());
        Assertions.assertEquals("restaurant:read", RESTAURANT_READ.getValue());
        Assertions.assertEquals("restaurant:write", RESTAURANT_WRITE.getValue());
        Assertions.assertEquals("user:write", USER_WRITE.getValue());
        Assertions.assertEquals("user:read", USER_READ.getValue());
        Assertions.assertNotNull(Authorities.values());
        Assertions.assertNotNull(Authorities.valueOf("SERVICE_WRITE"));
        Assertions.assertNotNull(Authorities.valueOf("SERVICE_READ"));
        Assertions.assertNotNull(Authorities.valueOf("USER_WRITE"));
        Assertions.assertNotNull(Authorities.valueOf("RESTAURANT_WRITE"));
        Assertions.assertNotNull(Authorities.valueOf("RESTAURANT_READ"));
        Assertions.assertNotNull(Authorities.valueOf("USER_READ"));
    }

    @Test
    void testCrudActions() {
        Assertions.assertEquals("DELETE", DELETE.name());
        Assertions.assertEquals("READ", READ.name());
        Assertions.assertEquals("ADD", ADD.name());
        Assertions.assertEquals("UPDATE", UPDATE.name());
        Assertions.assertNotNull(CrudActions.values());
        Assertions.assertNotNull(CrudActions.valueOf("READ"));
        Assertions.assertNotNull(CrudActions.valueOf("DELETE"));
        Assertions.assertNotNull(CrudActions.valueOf("ADD"));
        Assertions.assertNotNull(CrudActions.valueOf("UPDATE"));
    }

    @Test
    void testRoles() {
        Assertions.assertEquals("ADMIN", ADMIN.name());
        Assertions.assertEquals("DELIVERY", DELIVERY.name());
        Assertions.assertEquals("USER", USER.name());
        Assertions.assertEquals("RESTAURANT", RESTAURANT.name());
        Assertions.assertNotNull(UserTypes.values());
        Assertions.assertNotNull(UserTypes.valueOf("RESTAURANT"));
        Assertions.assertNotNull(UserTypes.valueOf("USER"));
        Assertions.assertNotNull(UserTypes.valueOf("ADMIN"));
        Assertions.assertNotNull(UserTypes.valueOf("DELIVERY"));
    }


}
