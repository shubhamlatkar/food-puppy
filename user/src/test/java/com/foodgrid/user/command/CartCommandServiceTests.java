package com.foodgrid.user.command;

import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.user.command.external.service.RestService;
import com.foodgrid.user.command.internal.event.broker.CartEventBroker;
import com.foodgrid.user.command.internal.model.aggregate.CartCommandModel;
import com.foodgrid.user.command.internal.model.entity.ItemCommandModel;
import com.foodgrid.user.command.internal.payload.dto.request.AddItemRequest;
import com.foodgrid.user.command.internal.repository.CartCommandRepository;
import com.foodgrid.user.command.internal.service.CartCommandService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CartCommandService.class})
@AutoConfigureWebTestClient
class CartCommandServiceTests {

    @MockBean
    private CartCommandRepository cartCommandRepository;
    @MockBean
    private UserSession userSession;
    @MockBean
    private RestService restService;
    @MockBean
    private CartEventBroker cartEventBroker;


    @Autowired
    private CartCommandService cartCommandService;

    @Test
    @DisplayName("Tests addItem method of CartCommandService")
    void addItem() {
        var results = new BindingResults();
        when(userSession.getUserId()).thenReturn("1");
        var set = new HashSet<ItemCommandModel>();
        set.add(new ItemCommandModel("1", "test", 12.1, 1));
        doAnswer(invocationOnMock -> java.util.Optional.of(new CartCommandModel("1", "1restaurantId", set)))
                .when(cartCommandRepository).findById(any());
        doAnswer(invocationOnMock -> null)
                .when(cartEventBroker).sendCartEvent(any());
        doAnswer(invocationOnMock -> null)
                .when(cartCommandRepository).delete(any());
        Assertions.assertNotNull(cartCommandService.addItem(new AddItemRequest("1", "1"), results));
    }

    @Test
    @DisplayName("Tests addNewItem for non existing item cart method of CartCommandService")
    void addNewItem() {
        var results = new BindingResults();
        when(userSession.getUserId()).thenReturn("1");
        when(restService.getItemShort(anyString(), anyString())).thenReturn(new ItemCommandModel("1", "test", 12.23, 1));
        var set = new HashSet<ItemCommandModel>();
        set.add(new ItemCommandModel("1", "test", 12.1, 1));
        doAnswer(invocationOnMock -> java.util.Optional.of(new CartCommandModel("1", "1restaurantId", set)))
                .when(cartCommandRepository).findById(any());
        doAnswer(invocationOnMock -> null)
                .when(cartEventBroker).sendCartEvent(any());
        doAnswer(invocationOnMock -> null)
                .when(cartCommandRepository).delete(any());
        Assertions.assertNotNull(cartCommandService.addItem(new AddItemRequest("1", "2"), results));
    }

    @Test
    @DisplayName("Tests removeItem method of CartCommandService")
    void removeItem() {
        when(userSession.getUserId()).thenReturn("1");
        when(userSession.getUserId()).thenReturn("1");
        var set = new HashSet<ItemCommandModel>();
        doAnswer(invocationOnMock -> java.util.Optional.of(new CartCommandModel("1", "1restaurantId", set)))
                .when(cartCommandRepository).findById(any());
        doAnswer(invocationOnMock -> null)
                .when(cartEventBroker).sendCartEvent(any());
        doAnswer(invocationOnMock -> null)
                .when(cartCommandRepository).save(any());
        Assertions.assertNotNull(cartCommandService.removeItem("1"));
    }

    @Test
    @DisplayName("Tests decreaseQuantity method of CartCommandService")
    void decreaseQuantity() {
        when(userSession.getUserId()).thenReturn("1");
        var set = new HashSet<ItemCommandModel>();
        set.add(new ItemCommandModel("1", "test", 12.1, 1));
        doAnswer(invocationOnMock -> java.util.Optional.of(new CartCommandModel("1", "1restaurantId", set)))
                .when(cartCommandRepository).findById(any());
        doAnswer(invocationOnMock -> null)
                .when(cartEventBroker).sendCartEvent(any());
        doAnswer(invocationOnMock -> null)
                .when(cartCommandRepository).save(any());
        Assertions.assertNotNull(cartCommandService.removeItem("1"));
    }

    @Test
    @DisplayName("Tests removeCart method of CartCommandService")
    void removeCart() {
        when(userSession.getUserId()).thenReturn("1");
        when(userSession.getUserId()).thenReturn("1");
        var set = new HashSet<ItemCommandModel>();
        doAnswer(invocationOnMock -> java.util.Optional.of(new CartCommandModel("1", "1restaurantId", set)))
                .when(cartCommandRepository).findById(any());
        doAnswer(invocationOnMock -> null)
                .when(cartEventBroker).sendCartEvent(any());
        doAnswer(invocationOnMock -> null)
                .when(cartCommandRepository).delete(any());
        cartCommandService.removeCart();
        Assertions.assertNotNull(userSession.getUserId());
    }
}
