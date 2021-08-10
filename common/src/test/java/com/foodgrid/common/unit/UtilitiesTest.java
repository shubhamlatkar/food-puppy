package com.foodgrid.common.unit;

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
        Assertions.assertNotNull(USER_READ.getValue());
        Assertions.assertNotNull(RESTAURANT_READ.getValue());
        Assertions.assertNotNull(RESTAURANT_WRITE.getValue());
        Assertions.assertNotNull(USER_WRITE.getValue());
        Assertions.assertNotNull(SERVICE_READ.getValue());
        Assertions.assertNotNull(SERVICE_WRITE.getValue());
    }

    @Test
    void testCrudActions() {
        Assertions.assertNotNull(DELETE.name());
        Assertions.assertNotNull(READ.name());
        Assertions.assertNotNull(DELETE.name());
        Assertions.assertNotNull(ADD.name());
        Assertions.assertNotNull(UPDATE.name());
    }

    @Test
    void testRoles() {
        Assertions.assertNotNull(ADMIN.name());
        Assertions.assertNotNull(DELIVERY.name());
        Assertions.assertNotNull(USER.name());
        Assertions.assertNotNull(RESTAURANT.name());
    }


}
