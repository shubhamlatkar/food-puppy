package com.foodgrid.user.command.internal.rest;

import com.foodgrid.user.command.internal.payload.dto.request.AddressRequest;
import com.foodgrid.user.command.internal.payload.dto.response.AddressOperationSuccess;
import com.foodgrid.user.command.internal.service.AddressCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/${endpoint.service}/${endpoint.version}")
public class AddressCommandController {

    private final AddressCommandService addressService;

    @Autowired
    public AddressCommandController(AddressCommandService addressService) {
        this.addressService = addressService;
    }

    @PutMapping("/${endpoint.user.address}")
    public ResponseEntity<AddressOperationSuccess> addAddress(@Valid @RequestBody AddressRequest address, BindingResult result) {
        return ResponseEntity.ok(addressService.addAddress(address, result));
    }

    @DeleteMapping("/${endpoint.user.address}/{addressId}")
    public ResponseEntity<AddressOperationSuccess> deleteAddressById(@PathVariable String addressId) {
        return ResponseEntity.ok(addressService.deleteAddressById(addressId));
    }

    @PatchMapping("/${endpoint.user.address}/{addressId}")
    public ResponseEntity<AddressOperationSuccess> patchAddress(@PathVariable String addressId, @Valid @RequestBody AddressRequest address, BindingResult result) {
        return ResponseEntity.ok(addressService.patchAddress(addressId, address, result));
    }
}
