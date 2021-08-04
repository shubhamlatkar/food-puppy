package com.foodgrid.user.query.internal.event.broker;

import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.user.query.internal.event.handler.CartEventHandler;
import com.foodgrid.user.query.internal.model.aggregate.AddressQueryModel;
import com.foodgrid.user.query.internal.service.AddressQueryService;
import com.foodgrid.user.shared.payload.AddressEventDto;
import com.foodgrid.user.shared.payload.CartEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventBroker {

    private final AddressQueryService addressQueryService;
    private final CartEventHandler cartEventHandler;

    @Autowired
    public EventBroker(AddressQueryService addressQueryService, CartEventHandler cartEventHandler) {
        this.addressQueryService = addressQueryService;
        this.cartEventHandler = cartEventHandler;
    }

    @JmsListener(destination = "${event.user.address}")
    public void addressBroker(AddressEventDto address) {
        if (address.getAction() != CrudActions.DELETE) {
            addressQueryService.patchAddress(new AddressQueryModel(address));
        } else {
            addressQueryService.deleteAddressById(address.getId());
        }
    }

    @JmsListener(destination = "${event.user.cart}")
    public void cartBroker(CartEventDTO cart) {
        switch (cart.getActivity()) {
            case ADD_ITEM:
                cartEventHandler.addItem(cart);
                break;
            case DECREASE_QUANTITY:
                cartEventHandler.decreaseQuantity(cart);
                break;
            case DELETE_CART:
                cartEventHandler.deleteCart(cart);
                break;
            case REMOVE_ITEM:
                cartEventHandler.removeItem(cart);
                break;
            case INCREASE_QUANTITY:
                cartEventHandler.increaseQuantity(cart);
                break;
            default:
                break;
        }
    }

}
