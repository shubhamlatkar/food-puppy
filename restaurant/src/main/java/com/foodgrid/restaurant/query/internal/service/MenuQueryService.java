package com.foodgrid.restaurant.query.internal.service;

import com.foodgrid.common.exception.exceptions.NotFoundException;
import com.foodgrid.common.payload.dto.response.GetItemResponse;
import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.restaurant.query.internal.model.aggregate.MenuQueryModel;
import com.foodgrid.restaurant.query.internal.model.entity.ItemQueryModel;
import com.foodgrid.restaurant.query.internal.repository.MenuQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MenuQueryService {

    private final MenuQueryRepository menuQueryRepository;
    private final UserSession userSession;

    @Autowired
    public MenuQueryService(MenuQueryRepository menuQueryRepository, UserSession userSession) {
        this.menuQueryRepository = menuQueryRepository;
        this.userSession = userSession;
    }

    public MenuQueryModel getMenu() {
        var menu = menuQueryRepository.findByRestaurantId(userSession.getUserId()).orElse(null);
        if (menu == null)
            throw new NotFoundException("Not found with id " + userSession.getUserId());
        log.info("Get menu for: {}", menu.getId());
        return menu;
    }

    public ItemQueryModel getItemById(String id) {
        var menu = menuQueryRepository.findByRestaurantId(userSession.getUserId()).orElse(null);
        if (menu == null)
            throw new NotFoundException("Not available for restaurant id " + userSession.getUserId());
        var item = menu.getItems().stream().filter(itemQueryModel -> itemQueryModel.getId().equals(id)).findFirst().orElse(null);
        if (item == null)
            throw new NotFoundException("Item not found with id " + id);
        log.info("Get item for item id: {}", id);
        return item;
    }

    public ItemQueryModel getItemByRestaurantIdAndItemId(String restaurantId, String itemId) {
        var menu = menuQueryRepository.findByRestaurantId(restaurantId).orElse(null);
        if (menu == null)
            throw new NotFoundException("Not found for restaurant id " + restaurantId);
        var item = menu.getItems().stream().filter(itemQueryModel -> itemQueryModel.getId().equals(itemId)).findFirst().orElse(null);
        if (item == null)
            throw new NotFoundException("Item Not found with id " + itemId);
        log.info("Get item for item id: {}", itemId);
        return item;
    }

    public MenuQueryModel getMenuForRestaurant(String restaurantId) {
        var menu = menuQueryRepository.findByRestaurantId(restaurantId).orElse(null);
        if (menu == null)
            throw new NotFoundException("Menu not found with id " + restaurantId);
        log.info("Get menu for: {}", menu.getId());
        return menu;
    }

    public GetItemResponse getItemByRestaurantIdAndItemIdShort(String restaurantId, String itemId) {
        var menu = menuQueryRepository.findByRestaurantId(restaurantId).orElse(null);
        if (menu == null)
            throw new NotFoundException("Not found for restaurant id " + restaurantId);
        var item = menu.getItems().stream().filter(itemQueryModel -> itemQueryModel.getId().equals(itemId)).findFirst().orElse(null);
        if (item == null)
            throw new NotFoundException("Item Not found with id " + itemId);
        log.info("Get item short for item id: {}", itemId);
        return new GetItemResponse(item.getId(), item.getName(), item.getValue());
    }
}
