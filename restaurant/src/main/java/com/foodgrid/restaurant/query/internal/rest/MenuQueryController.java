package com.foodgrid.restaurant.query.internal.rest;

import com.foodgrid.common.payload.dto.response.GetItemResponse;
import com.foodgrid.restaurant.query.internal.model.aggregate.MenuQueryModel;
import com.foodgrid.restaurant.query.internal.model.entity.ItemQueryModel;
import com.foodgrid.restaurant.query.internal.service.MenuQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/${endpoint.service}/${endpoint.version}")
public class MenuQueryController {

    private final MenuQueryService menuQueryService;

    @Autowired
    public MenuQueryController(MenuQueryService menuQueryService) {
        this.menuQueryService = menuQueryService;
    }

    @GetMapping("/${endpoint.restaurant.menu}")
    public ResponseEntity<MenuQueryModel> getMenuByRestaurantId() {
        return ResponseEntity.ok(menuQueryService.getMenu());
    }

    @GetMapping("/${endpoint.restaurant.menu}/${endpoint.restaurant.item}/{id}")
    public ResponseEntity<ItemQueryModel> getItemById(@PathVariable String id) {
        return ResponseEntity.ok(menuQueryService.getItemById(id));
    }

    @GetMapping("/${endpoint.public}/${endpoint.restaurant.menu}/${endpoint.restaurant.item}")
    public ResponseEntity<ItemQueryModel> getItemByRestaurantIdAndItemId(@RequestParam("restaurantId") String restaurantId, @RequestParam("itemId") String itemId) {
        return ResponseEntity.ok(menuQueryService.getItemByRestaurantIdAndItemId(restaurantId, itemId));
    }

    @GetMapping("/${endpoint.public}/${endpoint.restaurant.menu}/{restaurantId}")
    public ResponseEntity<MenuQueryModel> getMenuForRestaurant(@PathVariable String restaurantId) {
        return ResponseEntity.ok(menuQueryService.getMenuForRestaurant(restaurantId));
    }

    @GetMapping("/${endpoint.public}/${endpoint.short}/${endpoint.restaurant.menu}/${endpoint.restaurant.item}")
    public ResponseEntity<GetItemResponse> getItemByRestaurantIdAndItemIdShort(@RequestParam("restaurantId") String restaurantId, @RequestParam("itemId") String itemId) {
        return ResponseEntity.ok(menuQueryService.getItemByRestaurantIdAndItemIdShort(restaurantId, itemId));
    }
}
