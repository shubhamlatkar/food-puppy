package com.foodgrid.user.query.internal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemQueryModel {
    private String id;
    private String name;
    private Double value;
    private Integer quantity;
}
