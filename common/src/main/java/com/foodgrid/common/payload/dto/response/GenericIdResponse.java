package com.foodgrid.common.payload.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericIdResponse extends BaseResponse<String> {
    private String msg;

    public GenericIdResponse(String id, String msg) {
        super(id);
        this.msg = msg;
    }
}
