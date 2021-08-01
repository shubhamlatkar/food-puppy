package com.foodgrid.restaurant.shared.payload;

import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.restaurant.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.restaurant.shared.utility.DayTimings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuEventDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String restaurantId;
    private String itemId;
    private String name;
    private Double value;
    private Float rating;
    private String ingredient;
    private String comment;
    private List<TimingsEventDTO> timingsList;
    private CrudActions action;

    public MenuEventDTO(ItemRequest itemRequest, String id, String itemId, String restaurantId, Float rating, CrudActions action) {
        this.action = action;
        this.id = id;
        this.restaurantId = restaurantId;
        this.itemId = itemId;
        this.name = itemRequest.getName();
        this.value = itemRequest.getValue();
        this.rating = rating;
        this.ingredient = itemRequest.getIngredient();
        this.comment = itemRequest.getComment();
        this.timingsList = Arrays.asList(
                new TimingsEventDTO(DayTimings.MORNING, itemRequest.getStartFirst(), itemRequest.getEndFirst()),
                new TimingsEventDTO(DayTimings.EVENING, itemRequest.getStartSecond(), itemRequest.getEndSecond())
        );
    }
}
