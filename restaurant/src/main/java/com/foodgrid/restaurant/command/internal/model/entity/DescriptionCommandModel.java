package com.foodgrid.restaurant.command.internal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionCommandModel {
    private String ingredient;
    private String comment;
    private List<TimingsCommandModel> timingsCommandModelList;
}