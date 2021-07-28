package com.foodgrid.user.query.internal.service;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.user.query.internal.model.aggregate.AddressQueryModel;
import com.foodgrid.user.query.internal.payload.response.FindByUserId;
import com.foodgrid.user.query.internal.repository.AddressQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddressQueryService {

    private final AddressQueryRepository addressQueryRepository;

    @Autowired
    public AddressQueryService(AddressQueryRepository addressQueryRepository) {
        this.addressQueryRepository = addressQueryRepository;
    }

    public Boolean deleteAddressById(String id) {
        try {
            addressQueryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean patchAddress(AddressQueryModel addressQuery) {
        try {
            addressQueryRepository.save(addressQuery);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResponseEntity<FindByUserId> getAddressByUserId(String userId) {
        try {
            var addresses = addressQueryRepository.findByUserId(userId).orElse(null);
            return ResponseEntity.ok(new FindByUserId(userId, addresses));
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal err ");
        }
    }

    public ResponseEntity<AddressQueryModel> getAddressById(String addressId) {
        var address = addressQueryRepository.findById(addressId).orElse(null);
        if (address == null)
            return ResponseEntity.ok(new AddressQueryModel());
        return ResponseEntity.ok(address);
    }
}
