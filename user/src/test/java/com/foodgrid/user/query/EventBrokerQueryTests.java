package com.foodgrid.user.query;

import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.user.command.internal.model.entity.ItemCommandModel;
import com.foodgrid.user.command.internal.payload.dto.request.AddressRequest;
import com.foodgrid.user.query.internal.event.broker.EventBroker;
import com.foodgrid.user.query.internal.event.handler.CartEventHandler;
import com.foodgrid.user.query.internal.service.AddressQueryService;
import com.foodgrid.user.shared.model.Location;
import com.foodgrid.user.shared.payload.AddressEventDto;
import com.foodgrid.user.shared.payload.CartEventDTO;
import com.foodgrid.user.shared.utility.CartActivities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {EventBroker.class})
@AutoConfigureWebTestClient
class EventBrokerQueryTests {
    @MockBean
    private AddressQueryService addressQueryService;
    @MockBean
    private CartEventHandler cartEventHandler;

    @MockBean
    private UserSession userSession;

    @Autowired
    private EventBroker eventBroker;

    @Test
    @DisplayName("Tests addressBroker method of EventBroker")
    void addressBroker() {
        when(userSession.getUserId()).thenReturn("1");
        var address = new AddressRequest(new Location(12.1, 31.2), "home", "test line 1", "test line 2", "313131", "pune", "MH", true);
        doAnswer(invocationOnMock -> null)
                .when(addressQueryService).patchAddress(any());
        eventBroker.addressBroker(new AddressEventDto(address, "1", "1", CrudActions.ADD));
        Assertions.assertNotNull(userSession.getUserId());
    }

    @Test
    @DisplayName("Tests cartBroker method of EventBroker")
    void cartBroker() {
        when(userSession.getUserId()).thenReturn("1");
        var cart = new CartEventDTO(CartActivities.ADD_ITEM, new ItemCommandModel("1", "test", 12.23, 1), "1", "1");
        doAnswer(invocationOnMock -> null)
                .when(cartEventHandler).addItem(any());
        doAnswer(invocationOnMock -> null)
                .when(cartEventHandler).decreaseQuantity(any());
        doAnswer(invocationOnMock -> null)
                .when(cartEventHandler).addItem(any());
        doAnswer(invocationOnMock -> null)
                .when(cartEventHandler).increaseQuantity(any());
        doAnswer(invocationOnMock -> null)
                .when(cartEventHandler).removeItem(any());

        eventBroker.cartBroker(cart);
        cart.setActivity(CartActivities.DELETE_CART);
        eventBroker.cartBroker(cart);
        cart.setActivity(CartActivities.INCREASE_QUANTITY);
        eventBroker.cartBroker(cart);
        cart.setActivity(CartActivities.DECREASE_QUANTITY);
        eventBroker.cartBroker(cart);
        cart.setActivity(CartActivities.REMOVE_ITEM);
        eventBroker.cartBroker(cart);
        Assertions.assertNotNull(userSession.getUserId());
    }

}
