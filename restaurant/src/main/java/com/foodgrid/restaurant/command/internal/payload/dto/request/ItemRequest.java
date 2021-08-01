package com.foodgrid.restaurant.command.internal.payload.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {
    private String name;
    private Double value;
    private String ingredient;
    private String comment;
    private Integer startFirst;
    private Integer endFirst;
    private Integer startSecond;
    private Integer endSecond;
}
