package com.foodgrid.user.query;

import com.foodgrid.user.query.internal.payload.response.AddressNotFoundResponse;
import com.foodgrid.user.query.internal.payload.response.FindByAddressId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureWebTestClient
class ResponsesTests {

    @Test
    @DisplayName("Tests AddressNotFoundResponse ")
    void addressNotFoundResponse() {
        var addressNotFound = new AddressNotFoundResponse("1", "test");
        Assertions.assertNotNull(addressNotFound.getMsg());
    }

    @Test
    @DisplayName("Tests FindByAddressId ")
    void findByAddressId() {
        var findByAddressId = new FindByAddressId("1", "123");
        Assertions.assertNotNull(findByAddressId.getAddressId());
    }

}
