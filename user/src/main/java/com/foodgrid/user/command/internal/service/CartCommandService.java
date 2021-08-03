package com.foodgrid.user.command.internal.service;

import com.foodgrid.common.exception.exceptions.InvalidDataException;
import com.foodgrid.common.exception.exceptions.NotFoundException;
import com.foodgrid.common.payload.dto.response.GenericMessageResponse;
import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.user.command.external.service.RestService;
import com.foodgrid.user.command.internal.model.aggregate.CartCommandModel;
import com.foodgrid.user.command.internal.payload.dto.request.AddItemRequest;
import com.foodgrid.user.command.internal.repository.CartCommandRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashSet;

@Service
@Slf4j
public class CartCommandService {

    private final CartCommandRepository cartCommandRepository;
    private final UserSession userSession;
    private final RestService restService;

    @Autowired
    public CartCommandService(CartCommandRepository cartCommandRepository, UserSession userSession, RestService restService) {
        this.cartCommandRepository = cartCommandRepository;
        this.userSession = userSession;
        this.restService = restService;
    }

    public GenericMessageResponse addItem(AddItemRequest item, BindingResult result) {
        if (result.hasErrors())
            throw new InvalidDataException("Invalid data..");

        var cart = cartCommandRepository.findById(userSession.getUserId()).orElse(new CartCommandModel(userSession.getUserId(), item.getRestaurantId(), new HashSet<>()));
        cart
                .getItems()
                .stream()
                .filter(cartItem -> cartItem.getId().equals(item.getItemId()))
                .findFirst()
                .ifPresentOrElse(
                        existingItem -> cartCommandRepository.save(cart.increaseQuantity(item.getItemId())),
                        () -> cartCommandRepository.save(cart.addItem(restService.getItemShort(item.getRestaurantId(), item.getItemId())))
                );
        log.info("Item of id: {} added to cart for user: {}", item.getItemId(), userSession.getUserId());
        return new GenericMessageResponse("Added successfully...");
    }

    public GenericMessageResponse removeItem(String itemId) {
        cartCommandRepository.findById(userSession.getUserId())
                .ifPresentOrElse(
                        cart -> cartCommandRepository.save(cart.removeItem(itemId)),
                        () -> {
                            throw new NotFoundException("No item found for id : " + itemId);
                        }
                );
        log.info("Item of id: {} removed from cart for user: {}", itemId, userSession.getUserId());
        return new GenericMessageResponse("Removed successfully...");
    }

    public GenericMessageResponse decreaseQuantity(String itemId) {
        cartCommandRepository.findById(userSession.getUserId())
                .ifPresentOrElse(
                        cart -> cartCommandRepository.save(cart.decreaseQuantity(itemId)),
                        () -> {
                            throw new NotFoundException("No item found for id : " + itemId);
                        }
                );
        log.info("Quantity decreased for item of id: {} from cart for: {}", itemId, userSession.getUserId());
        return new GenericMessageResponse("Decreased successfully...");
    }
}
