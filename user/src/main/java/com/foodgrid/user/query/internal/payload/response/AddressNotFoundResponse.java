package com.foodgrid.user.query.internal.payload.response;

import com.foodgrid.common.payload.dto.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressNotFoundResponse extends BaseResponse<String> {
    private String msg;

    public AddressNotFoundResponse(String id, String msg) {
        super(id);
        this.msg = msg;
    }
}
