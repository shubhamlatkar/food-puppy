package com.foodgrid.user.query.internal.event.broker;

import com.foodgrid.user.query.internal.model.aggregate.AddressQueryModel;
import com.foodgrid.user.query.internal.service.AddressQueryService;
import com.foodgrid.user.shared.payload.AddressEventDto;
import com.foodgrid.user.shared.utility.AddressActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EventBroker {

    private final AddressQueryService addressQueryService;

    @Autowired
    public EventBroker(AddressQueryService addressQueryService) {
        this.addressQueryService = addressQueryService;
    }

    @JmsListener(destination = "${event.user.address}")
    public void addressBroker(AddressEventDto address) {
        if (address.getAction() != AddressActions.DELETE) {
            addressQueryService.patchAddress(new AddressQueryModel(address));
        } else {
            addressQueryService.deleteAddressById(address.getId());
        }
    }
}
