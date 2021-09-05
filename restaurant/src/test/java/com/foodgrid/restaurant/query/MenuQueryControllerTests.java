package com.foodgrid.restaurant.query;

import com.foodgrid.common.payload.dto.response.GetItemResponse;
import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.restaurant.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.restaurant.query.internal.model.aggregate.MenuQueryModel;
import com.foodgrid.restaurant.query.internal.model.entity.ItemQueryModel;
import com.foodgrid.restaurant.query.internal.rest.MenuQueryController;
import com.foodgrid.restaurant.query.internal.service.MenuQueryService;
import com.foodgrid.restaurant.shared.payload.MenuEventDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

@SpringBootTest(classes = {MenuQueryController.class})
@AutoConfigureWebTestClient
class MenuQueryControllerTests {


    @MockBean
    private MenuQueryService menuQueryService;

    @Autowired
    private MenuQueryController menuQueryController;

    @Test
    @DisplayName("Tests getMenuByRestaurantId method of MenuQueryController")
    void getMenuByRestaurantId() {
        doAnswer(invocationOnMock -> new MenuQueryModel("1", "1", null)).when(menuQueryService).getMenu();
        Assertions.assertNotNull(menuQueryController.getMenuByRestaurantId());
    }

    @Test
    @DisplayName("Tests getItemById method of MenuQueryController")
    void getItemById() {
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        var menu = new MenuEventDTO(itemRequest, "1", "1", "1", 3.2f, CrudActions.ADD);
        doAnswer(invocationOnMock -> new ItemQueryModel(menu)).when(menuQueryService).getItemById(anyString());
        Assertions.assertNotNull(menuQueryController.getItemById("1"));
    }


    @Test
    @DisplayName("Tests getItemByRestaurantIdAndItemId method of MenuQueryController")
    void getItemByRestaurantIdAndItemId() {
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        var menu = new MenuEventDTO(itemRequest, "1", "1", "1", 3.2f, CrudActions.ADD);
        doAnswer(invocationOnMock -> new ItemQueryModel(menu)).when(menuQueryService).getItemByRestaurantIdAndItemId(anyString(), anyString());
        Assertions.assertNotNull(menuQueryController.getItemByRestaurantIdAndItemId("1", "1"));
    }


    @Test
    @DisplayName("Tests getMenuForRestaurant method of MenuQueryController")
    void getMenuForRestaurant() {
        doAnswer(invocationOnMock -> new MenuQueryModel("1", "1", null)).when(menuQueryService).getMenuForRestaurant(anyString());
        Assertions.assertNotNull(menuQueryController.getMenuForRestaurant("1"));
    }


    @Test
    @DisplayName("Tests getItemByRestaurantIdAndItemIdShort method of MenuQueryController")
    void getItemByRestaurantIdAndItemIdShort() {
        doAnswer(invocationOnMock -> new GetItemResponse("1", "test", 12.12)).when(menuQueryService).getItemByRestaurantIdAndItemId(anyString(), anyString());
        Assertions.assertNotNull(menuQueryController.getItemByRestaurantIdAndItemIdShort("1", "1"));
    }


}
