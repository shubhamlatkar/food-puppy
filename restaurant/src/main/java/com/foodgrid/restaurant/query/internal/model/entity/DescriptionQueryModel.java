package com.foodgrid.restaurant.query.internal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionQueryModel {
    private String ingredient;
    private String comment;
    private List<TimingsQueryModel> timingsQueryModels;
}
