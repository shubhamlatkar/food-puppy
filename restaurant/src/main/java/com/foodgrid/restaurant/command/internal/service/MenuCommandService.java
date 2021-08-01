package com.foodgrid.restaurant.command.internal.service;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.common.exception.exceptions.NotFoundException;
import com.foodgrid.common.payload.dto.response.GenericIdResponse;
import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.restaurant.command.internal.event.outbound.MenuEventBroker;
import com.foodgrid.restaurant.command.internal.model.aggregate.MenuCommandModel;
import com.foodgrid.restaurant.command.internal.model.entity.ItemCommandModel;
import com.foodgrid.restaurant.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.restaurant.command.internal.repository.MenuCommandRepository;
import com.foodgrid.restaurant.shared.payload.MenuEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MenuCommandService {

    private final MenuCommandRepository menuCommandRepository;
    private final UserSession userSession;
    private final MenuEventBroker menuEventBroker;

    @Autowired
    public MenuCommandService(MenuCommandRepository menuCommandRepository, UserSession userSession, MenuEventBroker menuEventBroker) {
        this.menuCommandRepository = menuCommandRepository;
        this.userSession = userSession;
        this.menuEventBroker = menuEventBroker;
    }

    public MenuCommandModel addRestaurant(String restaurantId) {
        return menuCommandRepository.save(new MenuCommandModel(restaurantId));
    }

    public GenericIdResponse addItem(ItemRequest itemRequest) {
        try {
            var item = new ItemCommandModel(itemRequest);
            var res = new GenericIdResponse(item.getId(), "Added successfully....");
            menuCommandRepository
                    .findByRestaurantId(userSession.getUserId())
                    .ifPresentOrElse(
                            menuCommandModel -> {
                                log.info("Item command added successfully : {}", item);
                                var id = menuCommandRepository.save(menuCommandModel.addItem(item)).getId();
                                menuEventBroker.sendMenuEvent(new MenuEventDTO(itemRequest, id, item.getId(), userSession.getUserId(), 0.0f, CrudActions.ADD));
                            },
                            () -> {
                                log.info("Restaurant command saved and item added successfully : {}", item);
                                var menu = new MenuCommandModel(userSession.getUserId());
                                menu.addItem(item);
                                var id = menuCommandRepository.save(menu).getId();
                                menuEventBroker.sendMenuEvent(new MenuEventDTO(itemRequest, id, item.getId(), userSession.getUserId(), 0.0f, CrudActions.ADD));
                            });
            return res;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public GenericIdResponse removeItem(String itemId) {
        try {
            menuCommandRepository.findByRestaurantId(userSession.getUserId()).ifPresent(menuCommandModel -> {
                log.info("Item command removed successfully for id : {}", itemId);
                var id = menuCommandRepository.save(menuCommandModel.removeItem(itemId)).getId();
                menuEventBroker.sendMenuEvent(new MenuEventDTO(new ItemRequest(), id, itemId, userSession.getUserId(), null, CrudActions.DELETE));
            });
            return new GenericIdResponse(itemId, "ItemResponse removed successfully....");
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public GenericIdResponse removeMenu() {
        try {
            menuCommandRepository.findByRestaurantId(userSession.getUserId()).ifPresent(menuCommandModel -> {
                log.info("Menu command removed successfully for id : {}", userSession.getUserId());
                menuCommandRepository.delete(menuCommandModel);
                menuEventBroker.sendMenuEvent(new MenuEventDTO(new ItemRequest(), null, null, userSession.getUserId(), null, CrudActions.DELETE));
            });
            return new GenericIdResponse(userSession.getUserId(), "Menu removed successfully....");
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public GenericIdResponse updateItem(ItemRequest itemRequest, String itemId) {
        try {
            var menu = menuCommandRepository.findByRestaurantId(userSession.getUserId()).orElse(null);
            if (menu == null) {
                log.error("No data found for : {}", itemId);
                throw new NotFoundException("No data found for " + itemId);
            }
            var updatedMenu = menuCommandRepository.save(menu.patchItem(itemRequest, itemId));
            menuEventBroker.sendMenuEvent(new MenuEventDTO(itemRequest, updatedMenu.getId(), itemId, userSession.getUserId(), null, CrudActions.UPDATE));
            log.info("Item Command updated for : {}", itemId);
            return new GenericIdResponse(itemId, "Updated successfully");
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
