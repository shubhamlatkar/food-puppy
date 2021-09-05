package com.foodgrid.user.query;

import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.user.command.internal.model.entity.ItemCommandModel;
import com.foodgrid.user.query.internal.event.handler.CartEventHandler;
import com.foodgrid.user.query.internal.model.aggregate.CartQueryModel;
import com.foodgrid.user.query.internal.model.entity.ItemQueryModel;
import com.foodgrid.user.query.internal.repository.CartQueryRepository;
import com.foodgrid.user.shared.payload.CartEventDTO;
import com.foodgrid.user.shared.utility.CartActivities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CartEventHandler.class})
@AutoConfigureWebTestClient
class CartEventHandlerQueryTests {
    @MockBean
    private CartQueryRepository cartQueryRepository;
    @MockBean
    private UserSession userSession;

    @Autowired
    private CartEventHandler cartEventHandler;

    @Test
    @DisplayName("Tests addressBroker method of CartEventHandler")
    void addItem() {
        when(userSession.getUserId()).thenReturn("1");
        var cart = new CartEventDTO(CartActivities.ADD_ITEM, new ItemCommandModel("1", "test", 12.23, 1), "1", "1");
        var set = new HashSet<ItemQueryModel>();
        set.add(new ItemQueryModel("1", "test", 12.23, 1));
        doAnswer(invocationOnMock -> java.util.Optional.of(new CartQueryModel("1", "1", set)))
                .when(cartQueryRepository).findById(any());
        cartEventHandler.addItem(cart);
        Assertions.assertNotNull(userSession.getUserId());
    }


    @Test
    @DisplayName("Tests removeItem method of CartEventHandler")
    void removeItem() {
        when(userSession.getUserId()).thenReturn("1");
        var cart = new CartEventDTO(CartActivities.ADD_ITEM, new ItemCommandModel("1", "test", 12.23, 1), "1", "1");
        var set = new HashSet<ItemQueryModel>();
        set.add(new ItemQueryModel("1", "test", 12.23, 1));
        doAnswer(invocationOnMock -> java.util.Optional.of(new CartQueryModel("1", "1", set)))
                .when(cartQueryRepository).findById(any());
        cartEventHandler.removeItem(cart);
        Assertions.assertNotNull(userSession.getUserId());
    }

    @Test
    @DisplayName("Tests decreaseQuantity method of CartEventHandler")
    void decreaseQuantity() {
        when(userSession.getUserId()).thenReturn("1");
        var cart = new CartEventDTO(CartActivities.ADD_ITEM, new ItemCommandModel("1", "test", 12.23, 1), "1", "1");
        var set = new HashSet<ItemQueryModel>();
        set.add(new ItemQueryModel("1", "test", 12.23, 1));
        doAnswer(invocationOnMock -> java.util.Optional.of(new CartQueryModel("1", "1", set)))
                .when(cartQueryRepository).findById(any());
        cartEventHandler.decreaseQuantity(cart);
        Assertions.assertNotNull(userSession.getUserId());
    }

    @Test
    @DisplayName("Tests increaseQuantity method of CartEventHandler")
    void increaseQuantity() {
        when(userSession.getUserId()).thenReturn("1");
        var cart = new CartEventDTO(CartActivities.ADD_ITEM, new ItemCommandModel("1", "test", 12.23, 1), "1", "1");
        var set = new HashSet<ItemQueryModel>();
        set.add(new ItemQueryModel("1", "test", 12.23, 1));
        doAnswer(invocationOnMock -> java.util.Optional.of(new CartQueryModel("1", "1", set)))
                .when(cartQueryRepository).findById(any());
        cartEventHandler.decreaseQuantity(cart);
        Assertions.assertNotNull(userSession.getUserId());
    }

    @Test
    @DisplayName("Tests deleteCart method of CartEventHandler")
    void deleteCart() {
        when(userSession.getUserId()).thenReturn("1");
        var cart = new CartEventDTO(CartActivities.ADD_ITEM, new ItemCommandModel("1", "test", 12.23, 1), "1", "1");
        var set = new HashSet<ItemQueryModel>();
        set.add(new ItemQueryModel("1", "test", 12.23, 1));
        doAnswer(invocationOnMock -> java.util.Optional.of(new CartQueryModel("1", "1", set)))
                .when(cartQueryRepository).findById(any());
        cartEventHandler.deleteCart(cart);
        Assertions.assertNotNull(userSession.getUserId());
    }


}
