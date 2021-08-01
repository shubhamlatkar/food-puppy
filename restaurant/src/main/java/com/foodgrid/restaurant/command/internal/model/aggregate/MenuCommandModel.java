package com.foodgrid.restaurant.command.internal.model.aggregate;

import com.foodgrid.restaurant.command.internal.model.entity.DescriptionCommandModel;
import com.foodgrid.restaurant.command.internal.model.entity.ItemCommandModel;
import com.foodgrid.restaurant.command.internal.model.entity.TimingsCommandModel;
import com.foodgrid.restaurant.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.restaurant.shared.utility.DayTimings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuCommandModel {
    @Id
    private String id;
    private String restaurantId;
    private List<ItemCommandModel> itemCommandModels;

    public MenuCommandModel(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public MenuCommandModel addItem(ItemCommandModel itemCommandModel) {
        if (itemCommandModels == null)
            itemCommandModels = new ArrayList<>();
        itemCommandModels.add(itemCommandModel);
        return this;
    }

    public MenuCommandModel removeItem(String itemId) {
        if (itemCommandModels != null)
            itemCommandModels.removeIf(itemCommandModel1 -> itemCommandModel1.getId().equals(itemId));
        return this;
    }

    public MenuCommandModel patchItem(ItemRequest itemRequest, String itemId) {
        if (itemCommandModels != null) {
            itemCommandModels.forEach(itemCommandModel -> {
                if (itemCommandModel.getId().equals(itemId)) {
                    itemCommandModel.setName(itemRequest.getName());
                    itemCommandModel.setValue(itemRequest.getValue());
                    itemCommandModel.setDescriptionCommandModel(
                            new DescriptionCommandModel(
                                    itemRequest.getIngredient(),
                                    itemRequest.getComment(),
                                    Arrays.asList(
                                            new TimingsCommandModel(DayTimings.MORNING, itemRequest.getStartFirst(), itemRequest.getEndFirst()),
                                            new TimingsCommandModel(DayTimings.MORNING, itemRequest.getStartSecond(), itemRequest.getEndSecond())
                                    )));
                }
            });
        }
        return this;
    }
}
