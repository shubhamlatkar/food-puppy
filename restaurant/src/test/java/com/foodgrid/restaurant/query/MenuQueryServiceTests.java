package com.foodgrid.restaurant.query;

import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.restaurant.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.restaurant.query.internal.model.aggregate.MenuQueryModel;
import com.foodgrid.restaurant.query.internal.model.entity.ItemQueryModel;
import com.foodgrid.restaurant.query.internal.repository.MenuQueryRepository;
import com.foodgrid.restaurant.query.internal.service.MenuQueryService;
import com.foodgrid.restaurant.shared.payload.MenuEventDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {MenuQueryService.class})
@AutoConfigureWebTestClient
class MenuQueryServiceTests {
    @MockBean
    private MenuQueryRepository menuQueryRepository;
    @MockBean
    private UserSession userSession;

    @Autowired
    private MenuQueryService menuQueryService;

    @Test
    @DisplayName("Tests getMenu method of MenuQueryService")
    void getMenu() {
        when(userSession.getUserId()).thenReturn("1");
        doAnswer(invocationOnMock -> Optional.of(new MenuQueryModel("1", "1", null))).when(menuQueryRepository).findByRestaurantId(anyString());
        Assertions.assertNotNull(menuQueryService.getMenu());
    }

    @Test
    @DisplayName("Tests getItemById method of MenuQueryService")
    void getItemById() {
        when(userSession.getUserId()).thenReturn("1");
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        var menu = new MenuEventDTO(itemRequest, "1", "1", "1", 3.2f, CrudActions.ADD);
        doAnswer(invocationOnMock -> Optional.of(new MenuQueryModel("1", "1", List.of(new ItemQueryModel(menu))))).when(menuQueryRepository).findByRestaurantId(anyString());
        Assertions.assertNotNull(menuQueryService.getItemById("1"));
    }

    @Test
    @DisplayName("Tests getItemByRestaurantIdAndItemId method of MenuQueryService")
    void getItemByRestaurantIdAndItemId() {
        when(userSession.getUserId()).thenReturn("1");
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        var menu = new MenuEventDTO(itemRequest, "1", "1", "1", 3.2f, CrudActions.ADD);
        doAnswer(invocationOnMock -> Optional.of(new MenuQueryModel("1", "1", List.of(new ItemQueryModel(menu))))).when(menuQueryRepository).findByRestaurantId(anyString());
        Assertions.assertNotNull(menuQueryService.getItemByRestaurantIdAndItemId("1", "1"));
    }


    @Test
    @DisplayName("Tests getMenuForRestaurant method of MenuQueryService")
    void getMenuForRestaurant() {
        when(userSession.getUserId()).thenReturn("1");
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        var menu = new MenuEventDTO(itemRequest, "1", "1", "1", 3.2f, CrudActions.ADD);
        doAnswer(invocationOnMock -> Optional.of(new MenuQueryModel("1", "1", List.of(new ItemQueryModel(menu))))).when(menuQueryRepository).findByRestaurantId(anyString());
        Assertions.assertNotNull(menuQueryService.getMenuForRestaurant("1"));
    }

    @Test
    @DisplayName("Tests getItemByRestaurantIdAndItemIdShort method of MenuQueryService")
    void getItemByRestaurantIdAndItemIdShort() {
        when(userSession.getUserId()).thenReturn("1");
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        var menu = new MenuEventDTO(itemRequest, "1", "1", "1", 3.2f, CrudActions.ADD);
        doAnswer(invocationOnMock -> Optional.of(new MenuQueryModel("1", "1", List.of(new ItemQueryModel(menu))))).when(menuQueryRepository).findByRestaurantId(anyString());
        Assertions.assertNotNull(menuQueryService.getItemByRestaurantIdAndItemIdShort("1", "1"));
    }

}
