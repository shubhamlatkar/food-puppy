package com.foodgrid.user.query;

import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.user.query.internal.model.aggregate.CartQueryModel;
import com.foodgrid.user.query.internal.repository.CartQueryRepository;
import com.foodgrid.user.query.internal.service.CartQueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CartQueryService.class})
@AutoConfigureWebTestClient
class CartQueryServiceTests {

    @MockBean
    private CartQueryRepository cartQueryRepository;
    @MockBean
    private UserSession userSession;


    @Autowired
    private CartQueryService cartQueryService;

    @Test
    @DisplayName("Tests getCart method of CartQueryService")
    void getCart() {
        when(userSession.getUserId()).thenReturn("1");
        when(cartQueryRepository.findById(anyString())).thenReturn(java.util.Optional.of(new CartQueryModel("1", "1", null)));
        Assertions.assertNotNull(cartQueryService.getCart());
    }
}
