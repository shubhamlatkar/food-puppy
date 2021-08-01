package com.foodgrid.restaurant.query.internal.model.aggregate;

import com.foodgrid.restaurant.query.internal.model.entity.DescriptionQueryModel;
import com.foodgrid.restaurant.query.internal.model.entity.ItemQueryModel;
import com.foodgrid.restaurant.query.internal.model.entity.TimingsQueryModel;
import com.foodgrid.restaurant.shared.payload.MenuEventDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuQueryModel {
    @Id
    private String id;
    private String restaurantId;
    private List<ItemQueryModel> items;

    public MenuQueryModel(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public MenuQueryModel addItem(MenuEventDTO menu) {
        if (items == null)
            items = new ArrayList<>();
        items.add(new ItemQueryModel(menu));
        return this;
    }

    public MenuQueryModel removeItem(String itemId) {
        if (items != null)
            items.removeIf(itemQueryModel -> itemQueryModel.getId().equals(itemId));
        return this;
    }

    public MenuQueryModel patchItem(MenuEventDTO menu) {
        if (items != null) {
            items.forEach(itemQueryModel -> {
                if (itemQueryModel.getId().equals(menu.getItemId())) {
                    itemQueryModel.setName(menu.getName());
                    itemQueryModel.setValue(menu.getValue());
                    itemQueryModel.setDescription(
                            new DescriptionQueryModel(
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
                            )
                    );
                }
            });
        }
        return this;
    }
}
