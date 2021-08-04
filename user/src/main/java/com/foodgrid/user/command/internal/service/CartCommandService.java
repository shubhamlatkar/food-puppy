package com.foodgrid.user.command.internal.service;

import com.foodgrid.common.exception.exceptions.InvalidDataException;
import com.foodgrid.common.exception.exceptions.NotFoundException;
import com.foodgrid.common.payload.dto.response.GenericMessageResponse;
import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.user.command.external.service.RestService;
import com.foodgrid.user.command.internal.event.broker.CartEventBroker;
import com.foodgrid.user.command.internal.model.aggregate.CartCommandModel;
import com.foodgrid.user.command.internal.model.entity.ItemCommandModel;
import com.foodgrid.user.command.internal.payload.dto.request.AddItemRequest;
import com.foodgrid.user.command.internal.repository.CartCommandRepository;
import com.foodgrid.user.shared.payload.CartEventDTO;
import com.foodgrid.user.shared.utility.CartActivities;
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
    private final CartEventBroker cartEventBroker;

    @Autowired
    public CartCommandService(CartCommandRepository cartCommandRepository, UserSession userSession, RestService restService, CartEventBroker cartEventBroker) {
        this.cartCommandRepository = cartCommandRepository;
        this.userSession = userSession;
        this.restService = restService;
        this.cartEventBroker = cartEventBroker;
    }

    public void removeCart() {
        log.info("Cart Command cart deleted for user id: {}", userSession.getUserId());
        cartEventBroker.sendCartEvent(new CartEventDTO(CartActivities.DELETE_CART, new ItemCommandModel(), userSession.getUserId(), ""));
        cartCommandRepository.findById(userSession.getUserId()).ifPresent(cartCommandRepository::delete);
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
                        existingItem -> {
                            var updatedCart = cart.increaseQuantity(item.getItemId());
                            existingItem.setQuantity(existingItem.getQuantity() + 1);
                            cartEventBroker.sendCartEvent(new CartEventDTO(CartActivities.INCREASE_QUANTITY, existingItem, userSession.getUserId(), item.getRestaurantId()));
                            cartCommandRepository.save(updatedCart);
                        },
                        () -> {
                            var itemCommandModel = restService.getItemShort(item.getRestaurantId(), item.getItemId());
                            var updatedCart = cart.addItem(itemCommandModel);
                            cartEventBroker.sendCartEvent(new CartEventDTO(CartActivities.ADD_ITEM, itemCommandModel, userSession.getUserId(), item.getRestaurantId()));
                            cartCommandRepository.save(updatedCart);
                        }
                );
        log.info("Cart Command item of id: {} added to cart for user: {}", item.getItemId(), userSession.getUserId());
        return new GenericMessageResponse("Added successfully...");
    }

    public GenericMessageResponse removeItem(String itemId) {
        cartCommandRepository.findById(userSession.getUserId())
                .ifPresentOrElse(
                        cart -> {
                            cartEventBroker.sendCartEvent(new CartEventDTO(CartActivities.REMOVE_ITEM, new ItemCommandModel(itemId, "NA", 0.0, 0), userSession.getUserId(), cart.getRestaurantId()));
                            cartCommandRepository.save(cart.removeItem(itemId));
                        },
                        () -> {
                            throw new NotFoundException("No item found for id : " + itemId);
                        }
                );
        log.info("Cart Command item of id: {} removed from cart for user: {}", itemId, userSession.getUserId());
        return new GenericMessageResponse("Removed successfully...");
    }

    public GenericMessageResponse decreaseQuantity(String itemId) {
        cartCommandRepository.findById(userSession.getUserId())
                .ifPresentOrElse(
                        cart -> {
                            cartEventBroker.sendCartEvent(new CartEventDTO(CartActivities.DECREASE_QUANTITY, new ItemCommandModel(itemId, "NA", 0.0, 0), userSession.getUserId(), cart.getRestaurantId()));
                            cartCommandRepository.save(cart.decreaseQuantity(itemId));
                        },
                        () -> {
                            throw new NotFoundException("No item found for id : " + itemId);
                        }
                );
        log.info("Cart Command quantity decreased for item of id: {} from cart for: {}", itemId, userSession.getUserId());
        return new GenericMessageResponse("Decreased successfully...");
    }
}
