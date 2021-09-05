package com.foodgrid.restaurant.command;

import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.restaurant.command.internal.event.outbound.MenuEventBroker;
import com.foodgrid.restaurant.command.internal.model.aggregate.MenuCommandModel;
import com.foodgrid.restaurant.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.restaurant.command.internal.repository.MenuCommandRepository;
import com.foodgrid.restaurant.command.internal.service.MenuCommandService;
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

@SpringBootTest(classes = {MenuCommandService.class})
@AutoConfigureWebTestClient
class MenuCommandServiceTests {

    @MockBean
    private MenuCommandRepository menuCommandRepository;
    @MockBean
    private UserSession userSession;
    @MockBean
    private MenuEventBroker menuEventBroker;

    @Autowired
    private MenuCommandService menuCommandService;

    @Test
    @DisplayName("Tests addRestaurant method of MenuCommandService")
    void addRestaurant() {
        when(menuCommandRepository.save(any())).thenReturn(new MenuCommandModel("1", "1", null));
        Assertions.assertNotNull(menuCommandService.addRestaurant("1"));
    }

    @Test
    @DisplayName("Tests addItem in present one from MenuCommandService")
    void addItemPresent() {
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        when(menuCommandRepository.findByRestaurantId(any())).thenReturn(java.util.Optional.of(new MenuCommandModel("1", "1", null)));
        when(menuCommandRepository.save(any())).thenReturn(new MenuCommandModel("1", "1", null));
        doAnswer(invocationOnMock -> null)
                .when(menuEventBroker).sendMenuEvent(any());
        Assertions.assertNotNull(menuCommandService.addItem(itemRequest));
    }

    @Test
    @DisplayName("Tests addItem in blank one from MenuCommandService")
    void addItemNotPresent() {
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        when(menuCommandRepository.findByRestaurantId(any())).thenReturn(java.util.Optional.empty());
        doAnswer(invocationOnMock -> null)
                .when(menuEventBroker).sendMenuEvent(any());
        when(menuCommandRepository.save(any())).thenReturn(new MenuCommandModel("1", "1", null));
        Assertions.assertNotNull(menuCommandService.addItem(itemRequest));
    }

    @Test
    @DisplayName("Tests removeItem method of MenuCommandService")
    void removeItem() {
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        when(menuCommandRepository.findByRestaurantId(any())).thenReturn(java.util.Optional.of(new MenuCommandModel("1", "1", null)));
        doAnswer(invocationOnMock -> null)
                .when(menuEventBroker).sendMenuEvent(any());
        when(menuCommandRepository.save(any())).thenReturn(new MenuCommandModel("1", "1", null));
        Assertions.assertNotNull(menuCommandService.removeItem("1"));
    }


    @Test
    @DisplayName("Tests removeMenu method of MenuCommandService")
    void removeMenu() {
        when(menuCommandRepository.findByRestaurantId(any())).thenReturn(java.util.Optional.of(new MenuCommandModel("1", "1", null)));
        doAnswer(invocationOnMock -> null)
                .when(menuEventBroker).sendMenuEvent(any());
        doAnswer(invocationOnMock -> null).when(menuCommandRepository).delete(any());
        Assertions.assertNotNull(menuCommandService.removeMenu());
    }


    @Test
    @DisplayName("Tests updateItem method of MenuCommandService")
    void updateItem() {
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        when(menuCommandRepository.findByRestaurantId(any())).thenReturn(java.util.Optional.of(new MenuCommandModel("1", "1", null)));
        doAnswer(invocationOnMock -> null)
                .when(menuEventBroker).sendMenuEvent(any());
        when(menuCommandRepository.save(any())).thenReturn(new MenuCommandModel("1", "1", null));
        Assertions.assertNotNull(menuCommandService.updateItem(itemRequest, "1"));
    }

}
