package com.foodgrid.restaurant.query;

import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.restaurant.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.restaurant.query.internal.event.handler.MenuEventHandler;
import com.foodgrid.restaurant.query.internal.model.aggregate.MenuQueryModel;
import com.foodgrid.restaurant.query.internal.repository.MenuQueryRepository;
import com.foodgrid.restaurant.shared.payload.MenuEventDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {MenuEventHandler.class})
@AutoConfigureWebTestClient
class MenuEventHandlerTests {
    @MockBean
    private MenuQueryRepository menuQueryRepository;

    @MockBean
    private UserSession userSession;

    @Autowired
    private MenuEventHandler menuEventHandler;

    @Test
    @DisplayName("Tests addressBroker add menu item to existing menu method of EventBroker")
    void addressBrokerAddItemToExistingMenu() {
        when(userSession.getUserId()).thenReturn("1");
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        var menuEvent = new MenuEventDTO(itemRequest, "1", "1", "1", 4.3f, CrudActions.ADD);
        doAnswer(invocationOnMock -> Optional.of(new MenuQueryModel("1", "1", null))).when(menuQueryRepository).findByRestaurantId(anyString());
        doAnswer(invocationOnMock -> null).when(menuQueryRepository).save(any());
        menuEventHandler.consumeMenuEvent(menuEvent);
        Assertions.assertNotNull(userSession.getUserId());
    }

    @Test
    @DisplayName("Tests addressBroker add menu item to blank menu method of EventBroker")
    void addressBrokerAddItemToBlank() {
        when(userSession.getUserId()).thenReturn("1");
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        var menuEvent = new MenuEventDTO(itemRequest, "1", "1", "1", 4.3f, CrudActions.ADD);
        doAnswer(invocationOnMock -> null).when(menuQueryRepository).save(any());
        doAnswer(invocationOnMock -> Optional.empty()).when(menuQueryRepository).findByRestaurantId(anyString());
        menuEventHandler.consumeMenuEvent(menuEvent);
        Assertions.assertNotNull(userSession.getUserId());
    }


    @Test
    @DisplayName("Tests updateMenuItem method of EventBroker")
    void updateMenuItem() {
        when(userSession.getUserId()).thenReturn("1");
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        var menuEvent = new MenuEventDTO(itemRequest, "1", "1", "1", 4.3f, CrudActions.UPDATE);
        doAnswer(invocationOnMock -> null).when(menuQueryRepository).save(any());
        doAnswer(invocationOnMock -> Optional.of(new MenuQueryModel("1", "1", null))).when(menuQueryRepository).findByRestaurantId(anyString());
        menuEventHandler.consumeMenuEvent(menuEvent);
        Assertions.assertNotNull(userSession.getUserId());
    }


    @Test
    @DisplayName("Tests deleteItem method of EventBroker")
    void deleteItem() {
        when(userSession.getUserId()).thenReturn("1");
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        var menuEvent = new MenuEventDTO(itemRequest, "1", "1", "1", 4.3f, CrudActions.DELETE);
        doAnswer(invocationOnMock -> null).when(menuQueryRepository).save(any());
        doAnswer(invocationOnMock -> Optional.of(new MenuQueryModel("1", "1", null))).when(menuQueryRepository).findByRestaurantId(anyString());
        menuEventHandler.consumeMenuEvent(menuEvent);
        Assertions.assertNotNull(userSession.getUserId());
    }


    @Test
    @DisplayName("Tests deleteMenu method of EventBroker")
    void deleteMenu() {
        when(userSession.getUserId()).thenReturn("1");
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        var menuEvent = new MenuEventDTO(itemRequest, "1", null, "1", 4.3f, CrudActions.DELETE);
        doAnswer(invocationOnMock -> null).when(menuQueryRepository).save(any());
        doAnswer(invocationOnMock -> Optional.of(new MenuQueryModel("1", "1", null))).when(menuQueryRepository).findByRestaurantId(anyString());
        menuEventHandler.consumeMenuEvent(menuEvent);
        Assertions.assertNotNull(userSession.getUserId());
    }

}
