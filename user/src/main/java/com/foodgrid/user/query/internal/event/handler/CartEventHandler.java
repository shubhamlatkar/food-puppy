package com.foodgrid.user.query.internal.event.handler;

import com.foodgrid.user.query.internal.model.aggregate.CartQueryModel;
import com.foodgrid.user.query.internal.repository.CartQueryRepository;
import com.foodgrid.user.shared.payload.CartEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CartEventHandler {

    private final CartQueryRepository cartQueryRepository;

    @Autowired
    public CartEventHandler(CartQueryRepository cartQueryRepository) {
        this.cartQueryRepository = cartQueryRepository;
    }

    public void addItem(CartEventDTO cart) {
        cartQueryRepository
                .findById(cart.getUserId())
                .ifPresentOrElse(
                        existingCart -> cartQueryRepository.save(existingCart.addItem(cart)),
                        () -> {
                            var newCart = new CartQueryModel(cart.getUserId(), cart.getRestaurantId());
                            cartQueryRepository.save(newCart.addItem(cart));
                        });
        log.info("Cart query model item added for id: {}", cart.getItemId());
    }

    public void removeItem(CartEventDTO cart) {
        cartQueryRepository.findById(cart.getUserId()).ifPresent(existingCart -> cartQueryRepository.save(existingCart.removeItem(cart.getItemId())));
        log.info("Item id: {} removed from cart for user with id: {}", cart.getItemId(), cart.getUserId());
    }

    public void decreaseQuantity(CartEventDTO cart) {
        cartQueryRepository.findById(cart.getUserId()).ifPresent(existingCart -> cartQueryRepository.save(existingCart.decreaseQuantity(cart.getItemId())));
        log.info("Item id: {} decreased from cart for user with id: {}", cart.getItemId(), cart.getUserId());
    }


    public void increaseQuantity(CartEventDTO cart) {
        cartQueryRepository.findById(cart.getUserId()).ifPresent(existingCart -> cartQueryRepository.save(existingCart.increaseQuantity(cart.getItemId())));
        log.info("Item id: {} increased from cart for user with id: {}", cart.getItemId(), cart.getUserId());
    }

    public void deleteCart(CartEventDTO cart) {
        cartQueryRepository.findById(cart.getUserId()).ifPresent(cartQueryRepository::delete);
        log.info("Cart Query deleted for user with id: {}", cart.getUserId());
    }
}
