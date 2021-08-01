package com.foodgrid.restaurant.query.internal.event.handler;

import com.foodgrid.restaurant.query.internal.model.aggregate.MenuQueryModel;
import com.foodgrid.restaurant.query.internal.repository.MenuQueryRepository;
import com.foodgrid.restaurant.shared.payload.MenuEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MenuEventHandler {
    private final MenuQueryRepository menuQueryRepository;

    @Autowired
    public MenuEventHandler(MenuQueryRepository menuQueryRepository) {
        this.menuQueryRepository = menuQueryRepository;
    }

    public void consumeMenuEvent(MenuEventDTO menu) {
        switch (menu.getAction()) {
            case ADD:
                addMenuItem(menu);
                break;
            case UPDATE:
                updateMenuItem(menu);
                break;
            case DELETE:
                if (menu.getItemId() == null)
                    deleteMenu(menu);
                else
                    deleteItem(menu);
                break;
            default:
                break;
        }
    }

    private void addMenuItem(MenuEventDTO menu) {
        log.info("Menu query item added: {}", menu);
        menuQueryRepository.findByRestaurantId(menu.getRestaurantId())
                .ifPresentOrElse(
                        menu1 -> menuQueryRepository.save(menu1.addItem(menu)),
                        () -> {
                            var menuQuery = new MenuQueryModel(menu.getRestaurantId());
                            menuQuery.addItem(menu);
                            menuQueryRepository.save(menuQuery);
                        }
                );
    }

    private void updateMenuItem(MenuEventDTO menu) {
        log.info("Menu query item updated: {}", menu);
        menuQueryRepository.findByRestaurantId(menu.getRestaurantId())
                .ifPresent(menu1 -> menuQueryRepository.save(menu1.patchItem(menu)));
    }

    private void deleteItem(MenuEventDTO menu) {
        log.info("Menu query item deleted: {}", menu);
        menuQueryRepository.findByRestaurantId(menu.getRestaurantId())
                .ifPresent(menu1 -> menuQueryRepository.save(menu1.removeItem(menu.getItemId())));
    }

    private void deleteMenu(MenuEventDTO menu) {
        log.info("Menu query deleted: {}", menu);
        menuQueryRepository.findByRestaurantId(menu.getRestaurantId()).ifPresent(menuQueryRepository::delete);
    }
}
