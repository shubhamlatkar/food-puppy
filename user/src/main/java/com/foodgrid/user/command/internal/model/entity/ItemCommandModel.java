package com.foodgrid.user.command.internal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCommandModel {
    private String id;
    private String name;
    private Double value;
    private Integer quantity;
}
