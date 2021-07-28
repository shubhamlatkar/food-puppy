package com.foodgrid.user.query.internal.payload.response;

import com.foodgrid.common.payload.dto.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindByAddressId extends BaseResponse<String> {
    private String addressId;

    public FindByAddressId(String id, String addressId) {
        super(id);
        this.addressId = addressId;
    }
}
