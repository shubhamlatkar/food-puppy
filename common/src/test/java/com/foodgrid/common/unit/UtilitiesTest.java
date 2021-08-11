package com.foodgrid.common.unit;

import com.foodgrid.common.utility.Authorities;
import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.common.utility.UserTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import static com.foodgrid.common.utility.Authorities.*;
import static com.foodgrid.common.utility.Roles.*;

@SpringBootTest
@AutoConfigureWebTestClient
class UtilitiesTest {

    @Test
    void testAuthorities() {
        Assertions.assertNotNull(USER_READ.getValue());
        Assertions.assertNotNull(RESTAURANT_READ.getValue());
        Assertions.assertNotNull(RESTAURANT_WRITE.getValue());
        Assertions.assertNotNull(USER_WRITE.getValue());
        Assertions.assertNotNull(SERVICE_READ.getValue());
        Assertions.assertNotNull(SERVICE_WRITE.getValue());
        Assertions.assertNotNull(Authorities.valueOf("SERVICE_WRITE"));
        Assertions.assertNotNull(Authorities.valueOf("SERVICE_READ"));
        Assertions.assertNotNull(Authorities.valueOf("USER_WRITE"));
        Assertions.assertNotNull(Authorities.valueOf("RESTAURANT_WRITE"));
        Assertions.assertNotNull(Authorities.valueOf("RESTAURANT_READ"));
        Assertions.assertNotNull(Authorities.valueOf("USER_READ"));
    }

    @Test
    void testCrudActions() {
        Assertions.assertNotNull(CrudActions.valueOf("DELETE"));
        Assertions.assertNotNull(CrudActions.valueOf("READ"));
        Assertions.assertNotNull(CrudActions.valueOf("DELETE"));
        Assertions.assertNotNull(CrudActions.valueOf("ADD"));
        Assertions.assertNotNull(CrudActions.valueOf("UPDATE"));
    }

    @Test
    void testRoles() {
        Assertions.assertNotNull(ADMIN.name());
        Assertions.assertNotNull(DELIVERY.name());
        Assertions.assertNotNull(USER.name());
        Assertions.assertNotNull(RESTAURANT.name());
        Assertions.assertNotNull(UserTypes.valueOf("RESTAURANT"));
        Assertions.assertNotNull(UserTypes.valueOf("USER"));
        Assertions.assertNotNull(UserTypes.valueOf("ADMIN"));
        Assertions.assertNotNull(UserTypes.valueOf("DELIVERY"));
    }


}
