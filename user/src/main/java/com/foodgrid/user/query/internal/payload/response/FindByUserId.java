package com.foodgrid.user.query.internal.payload.response;

import com.foodgrid.common.payload.dto.response.BaseResponse;
import com.foodgrid.user.query.internal.model.aggregate.AddressQueryModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class FindByUserId extends BaseResponse<String> {
    private List<AddressQueryModel> addresses;

    public FindByUserId(String id, List<AddressQueryModel> addresses) {
        super(id);
        this.addresses = addresses;
    }
}
