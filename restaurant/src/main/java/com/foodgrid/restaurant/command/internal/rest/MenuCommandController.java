package com.foodgrid.restaurant.command.internal.rest;

import com.foodgrid.common.payload.dto.response.GenericIdResponse;
import com.foodgrid.restaurant.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.restaurant.command.internal.service.MenuCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/${endpoint.service}/${endpoint.version}")
public class MenuCommandController {

    private final MenuCommandService menuCommandService;

    @Autowired
    public MenuCommandController(MenuCommandService menuCommandService) {
        this.menuCommandService = menuCommandService;
    }

    @PutMapping("/${endpoint.restaurant.menu}/${endpoint.restaurant.item}")
    public ResponseEntity<GenericIdResponse> addItemToMenu(@Valid @RequestBody ItemRequest itemRequest, BindingResult result) {
        return ResponseEntity.ok(menuCommandService.addItem(itemRequest));
    }

    @DeleteMapping("/${endpoint.restaurant.menu}/${endpoint.restaurant.item}/{id}")
    public ResponseEntity<GenericIdResponse> deleteItem(@PathVariable String id) {
        return ResponseEntity.ok(menuCommandService.removeItem(id));
    }

    @PatchMapping("/${endpoint.restaurant.menu}/${endpoint.restaurant.item}/{id}")
    public ResponseEntity<GenericIdResponse> patchItem(@PathVariable String id, @RequestBody ItemRequest itemRequest) {
        return ResponseEntity.ok(menuCommandService.updateItem(itemRequest, id));
    }
}
