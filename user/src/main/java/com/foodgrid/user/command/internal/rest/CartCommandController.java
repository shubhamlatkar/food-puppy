package com.foodgrid.user.command.internal.rest;

import com.foodgrid.common.payload.dto.response.GenericMessageResponse;
import com.foodgrid.user.command.internal.payload.dto.request.AddItemRequest;
import com.foodgrid.user.command.internal.service.CartCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/${endpoint.service}/${endpoint.version}")
public class CartCommandController {
    private final CartCommandService cartCommandService;

    @Autowired
    public CartCommandController(CartCommandService cartCommandService) {
        this.cartCommandService = cartCommandService;
    }

    @PutMapping("/${endpoint.user.cart}/${endpoint.user.item}")
    public ResponseEntity<GenericMessageResponse> addItem(@Valid @RequestBody AddItemRequest item, BindingResult result) {
        return ResponseEntity.ok(cartCommandService.addItem(item, result));
    }

    @DeleteMapping("/${endpoint.user.cart}/${endpoint.user.item}/${endpoint.user.remove}/{itemId}")
    public ResponseEntity<GenericMessageResponse> remoteItem(@PathVariable String itemId) {
        return ResponseEntity.ok(cartCommandService.removeItem(itemId));
    }

    @DeleteMapping("/${endpoint.user.cart}/${endpoint.user.item}/{itemId}")
    public ResponseEntity<GenericMessageResponse> decreaseQuantity(@PathVariable String itemId) {
        return ResponseEntity.ok(cartCommandService.decreaseQuantity(itemId));
    }
}
