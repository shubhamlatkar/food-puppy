package com.foodgrid.restaurant.query.internal.model.entity;

import com.foodgrid.restaurant.shared.payload.MenuEventDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemQueryModel {
    private String id;
    private String name;
    private Double value;
    private DescriptionQueryModel description;
    private Float rating;

    public ItemQueryModel(MenuEventDTO menu) {
        this.id = menu.getItemId();
        this.name = menu.getName();
        this.value = menu.getValue();
        this.rating = menu.getRating();
        this.description = new DescriptionQueryModel(
                menu.getIngredient(),
                menu.getComment(),
                menu.getTimingsList()
                        .stream()
                        .map(timingsEventDTO ->
                                new TimingsQueryModel(
                                        timingsEventDTO.getDayTiming(),
                                        timingsEventDTO.getStart(),
                                        timingsEventDTO.getEnd()
                                ))
                        .collect(Collectors.toList())
        );
    }
}
