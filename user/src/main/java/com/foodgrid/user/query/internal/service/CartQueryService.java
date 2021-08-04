package com.foodgrid.user.query.internal.service;

import com.foodgrid.common.exception.exceptions.NotFoundException;
import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.user.query.internal.model.aggregate.CartQueryModel;
import com.foodgrid.user.query.internal.repository.CartQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CartQueryService {

    private final CartQueryRepository cartQueryRepository;
    private final UserSession userSession;

    @Autowired
    public CartQueryService(CartQueryRepository cartQueryRepository, UserSession userSession) {
        this.cartQueryRepository = cartQueryRepository;
        this.userSession = userSession;
    }

    public CartQueryModel getCart() {
        var cart = cartQueryRepository.findById(userSession.getUserId()).orElse(null);
        if (cart == null)
            throw new NotFoundException("Cart not found for user id: " + userSession.getUserId());
        return cart;
    }
}
