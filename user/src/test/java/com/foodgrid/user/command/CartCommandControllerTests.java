package com.foodgrid.user.command;

import com.foodgrid.common.payload.dto.response.GenericMessageResponse;
import com.foodgrid.user.command.internal.payload.dto.request.AddItemRequest;
import com.foodgrid.user.command.internal.rest.CartCommandController;
import com.foodgrid.user.command.internal.service.CartCommandService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CartCommandController.class})
@AutoConfigureWebTestClient
class CartCommandControllerTests {

    @MockBean
    private CartCommandService cartCommandService;

    @Autowired
    private CartCommandController cartCommandController;

    @Test
    @DisplayName("Tests addItem method of CartCommandController controller")
    void addItem() {
        var item = new AddItemRequest("1", "1");
        var result = new BindingResults();
        when(cartCommandService.addItem(item, result)).thenReturn(new GenericMessageResponse("test msg"));
        Assertions.assertNotNull(cartCommandController.addItem(item, result));
    }

    @Test
    @DisplayName("Tests remoteItem method of CartCommandController controller")
    void remoteItem() {
        when(cartCommandService.removeItem("1")).thenReturn(new GenericMessageResponse("test msg"));
        Assertions.assertNotNull(cartCommandController.remoteItem("1"));
    }

    @Test
    @DisplayName("Tests decreaseQuantity method of CartCommandController controller")
    void decreaseQuantity() {
        when(cartCommandService.decreaseQuantity("1")).thenReturn(new GenericMessageResponse("test msg"));
        Assertions.assertNotNull(cartCommandController.decreaseQuantity("1"));
    }
}
