package com.foodgrid.user.query.internal.rest;

import com.foodgrid.user.query.internal.model.aggregate.CartQueryModel;
import com.foodgrid.user.query.internal.service.CartQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/${endpoint.service}/${endpoint.version}")
public class CartQueryController {

    private final CartQueryService cartQueryService;

    @Autowired
    public CartQueryController(CartQueryService cartQueryService) {
        this.cartQueryService = cartQueryService;
    }

    @GetMapping("/${endpoint.user.cart}")
    public ResponseEntity<CartQueryModel> getCart() {
        return ResponseEntity.ok(cartQueryService.getCart());
    }
}
