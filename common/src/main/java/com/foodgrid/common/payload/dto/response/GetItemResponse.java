package com.foodgrid.common.payload.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetItemResponse {
    private String id;
    private String name;
    private Double value;
}
