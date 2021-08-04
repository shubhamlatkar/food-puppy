package com.foodgrid.user.query.internal.service;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.common.exception.exceptions.NotFoundException;
import com.foodgrid.user.query.internal.model.aggregate.AddressQueryModel;
import com.foodgrid.user.query.internal.payload.response.FindByUserId;
import com.foodgrid.user.query.internal.repository.AddressQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AddressQueryService {

    private final AddressQueryRepository addressQueryRepository;

    @Autowired
    public AddressQueryService(AddressQueryRepository addressQueryRepository) {
        this.addressQueryRepository = addressQueryRepository;
    }

    public Boolean deleteAddressById(String id) {
        try {
            addressQueryRepository.deleteById(id);
            log.info("Deleted query address by id:{}", id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean patchAddress(AddressQueryModel addressQuery) {
        try {
            addressQueryRepository.save(addressQuery);
            log.info("Patched query address for: {}", addressQuery);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public FindByUserId getAddressByUserId(String userId) {
        try {
            var addresses = addressQueryRepository.findByUserId(userId).orElse(null);
            log.info("Get address query for user id:{}", userId);
            return new FindByUserId(userId, addresses);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal err ");
        }
    }

    public AddressQueryModel getAddressById(String addressId) {
        var address = addressQueryRepository.findById(addressId).orElse(null);
        if (address == null) {
            throw new NotFoundException("Address not found for id: " + addressId);
        }
        log.info("Get address query for address id: {}", addressId);
        return address;
    }
}
