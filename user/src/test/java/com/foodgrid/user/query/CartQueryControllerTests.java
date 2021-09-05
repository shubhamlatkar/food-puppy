package com.foodgrid.user.query;

import com.foodgrid.user.query.internal.model.aggregate.CartQueryModel;
import com.foodgrid.user.query.internal.rest.CartQueryController;
import com.foodgrid.user.query.internal.service.CartQueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CartQueryController.class})
@AutoConfigureWebTestClient
class CartQueryControllerTests {

    @MockBean
    private CartQueryService cartQueryService;

    @Autowired
    private CartQueryController cartQueryController;

    @Test
    @DisplayName("Tests getCart method of CartQueryController")
    void getCart() {
        when(cartQueryService.getCart()).thenReturn(new CartQueryModel("1", "1", null));
        Assertions.assertNotNull(cartQueryController.getCart());
    }
}
