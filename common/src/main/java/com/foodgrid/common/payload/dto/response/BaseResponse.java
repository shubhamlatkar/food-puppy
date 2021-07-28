package com.foodgrid.common.payload.dto.response;

public class BaseResponse<T> {

    public final T id;

    public BaseResponse(T id) {
        this.id = id;
    }
}
