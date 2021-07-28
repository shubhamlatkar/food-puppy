package com.foodgrid.user.command.internal.service;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.common.exception.exceptions.InvalidDataException;
import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.user.command.internal.event.broker.AddressEventBroker;
import com.foodgrid.user.command.internal.model.aggregate.AddressCommandModel;
import com.foodgrid.user.command.internal.payload.dto.request.AddressRequest;
import com.foodgrid.user.command.internal.payload.dto.response.AddressOperationSuccess;
import com.foodgrid.user.command.internal.repository.AddressCommandRepository;
import com.foodgrid.user.shared.model.AddressDetails;
import com.foodgrid.user.shared.payload.AddressEventDto;
import com.foodgrid.user.shared.utility.AddressActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class AddressCommandService {
    private final AddressCommandRepository addressRepository;
    private final AddressEventBroker addressEventBroker;
    private final UserSession userSession;

    @Autowired
    public AddressCommandService(AddressCommandRepository addressRepository, AddressEventBroker addressEventBroker, UserSession userSession) {
        this.addressRepository = addressRepository;
        this.addressEventBroker = addressEventBroker;
        this.userSession = userSession;
    }

    public ResponseEntity<AddressOperationSuccess> addAddress(AddressRequest address, BindingResult result) {
        if (result.hasErrors())
            throw new InvalidDataException("Invalid address");
        else {
            String id;
            if (Boolean.FALSE.equals(address.getIsSelected()) && addressRepository.findByUserId(userSession.getUserId()).isPresent())
                address.setIsSelected(true);
            try {
                var addressCommand = new AddressCommandModel(address, userSession.getUserId());
                id = addressRepository.save(addressCommand).getId();
                var addressEvent = new AddressEventDto(address, userSession.getUserId(), id, AddressActions.ADD);
                addressEventBroker.sendAddressEvent(addressEvent);
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException("Internal server error for address repository");
            }
            if (id != null)
                return ResponseEntity.ok(new AddressOperationSuccess(id, "Address added successfully"));
            else
                throw new InvalidDataException("Invalid data");
        }
    }

    public ResponseEntity<AddressOperationSuccess> deleteAddressById(String addressId) {
        try {
            addressRepository.deleteById(addressId);
            var addressDto = new AddressEventDto();
            addressDto.setAction(AddressActions.DELETE);
            addressDto.setId(addressId);
            addressEventBroker.sendAddressEvent(addressDto);
        } catch (Exception e) {
            throw new InvalidDataException("Invalid address id");
        }
        return ResponseEntity.ok(new AddressOperationSuccess(addressId, "Deleted successfully"));
    }

    public ResponseEntity<AddressOperationSuccess> patchAddress(String addressId, AddressRequest address, BindingResult result) {
        if (result.hasErrors())
            throw new InvalidDataException("Invalid address");
        var existingAddress = addressRepository.findById(addressId).orElse(null);
        if (existingAddress != null) {
            existingAddress.setAddressDetails(new AddressDetails(address.getAddressLineOne(), address.getAddressLineTwo(), address.getPin(), address.getCity(), address.getState()));
            existingAddress.setName(address.getName());
            existingAddress.setLocation(address.getLocation());
            existingAddress.setIsSelected(address.getIsSelected());
            addressRepository.save(existingAddress);
            var addressEvent = new AddressEventDto(existingAddress, AddressActions.UPDATE);
            addressEventBroker.sendAddressEvent(addressEvent);
            return ResponseEntity.ok().body(new AddressOperationSuccess(addressId, "Patched successfully"));
        } else return ResponseEntity.ok().body(new AddressOperationSuccess(addressId, "Not Found"));
    }
}
