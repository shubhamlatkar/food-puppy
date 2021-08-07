package com.foodgrid.restaurant.unit;

import com.foodgrid.common.payload.dto.response.GenericIdResponse;
import com.foodgrid.common.security.service.AuthenticationService;
import com.foodgrid.restaurant.command.internal.service.MenuCommandService;
import com.foodgrid.restaurant.command.internal.service.RestaurantAuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
class RestaurantAuthenticationServiceTests {

    @Autowired
    private RestaurantAuthenticationService restaurantAuthenticationService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private MenuCommandService menuCommandService;

    @Test
    void testRestaurantAuthenticationServiceDeleteMe() {
        var testMsg = "Test delete";
        when(menuCommandService.removeMenu()).thenReturn(new GenericIdResponse("1", testMsg));
        when(authenticationService.delete()).thenReturn(new GenericIdResponse("1", testMsg));
        Assertions.assertEquals(testMsg, restaurantAuthenticationService.deleteMe().getMsg());
    }


}
