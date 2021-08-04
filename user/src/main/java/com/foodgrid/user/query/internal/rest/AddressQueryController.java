package com.foodgrid.user.query.internal.rest;

import com.foodgrid.user.query.internal.model.aggregate.AddressQueryModel;
import com.foodgrid.user.query.internal.payload.response.FindByUserId;
import com.foodgrid.user.query.internal.service.AddressQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/${endpoint.service}/${endpoint.version}")
public class AddressQueryController {

    private final AddressQueryService addressService;

    @Autowired
    public AddressQueryController(AddressQueryService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/${endpoint.user.address}/user/{userId}")
    public ResponseEntity<FindByUserId> getAddressByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(addressService.getAddressByUserId(userId));
    }

    @GetMapping("/${endpoint.user.address}/{addressId}")
    public ResponseEntity<AddressQueryModel> getAddressById(@PathVariable String addressId) {
        return ResponseEntity.ok(addressService.getAddressById(addressId));
    }
}
