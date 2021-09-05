package com.foodgrid.restaurant.command;

import com.foodgrid.common.payload.dto.response.GenericIdResponse;
import com.foodgrid.restaurant.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.restaurant.command.internal.rest.MenuCommandController;
import com.foodgrid.restaurant.command.internal.service.MenuCommandService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {MenuCommandController.class})
@AutoConfigureWebTestClient
class MenuCommandControllerTests {

    @MockBean
    private MenuCommandService menuCommandService;

    @Autowired
    private MenuCommandController menuCommandController;

    @Test
    @DisplayName("Tests addItemToMenu method of MenuCommandController")
    void addItemToMenu() {
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        var result = new BindingResults();
        when(menuCommandService.addItem(itemRequest)).thenReturn(new GenericIdResponse("1", "test"));
        Assertions.assertNotNull(menuCommandController.addItemToMenu(itemRequest, result));
    }

    @Test
    @DisplayName("Tests deleteItem method of MenuCommandController")
    void deleteItem() {
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        when(menuCommandService.removeItem("1")).thenReturn(new GenericIdResponse("1", "test"));
        Assertions.assertNotNull(menuCommandController.deleteItem("1"));
    }

    @Test
    @DisplayName("Tests patchItem method of MenuCommandController")
    void patchItem() {
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        when(menuCommandService.updateItem(itemRequest, "1")).thenReturn(new GenericIdResponse("1", "test"));
        Assertions.assertNotNull(menuCommandController.patchItem("1", itemRequest));
    }


}
