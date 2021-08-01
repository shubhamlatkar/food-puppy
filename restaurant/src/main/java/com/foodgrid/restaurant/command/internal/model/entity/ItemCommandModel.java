package com.foodgrid.restaurant.command.internal.model.entity;

import com.foodgrid.restaurant.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.restaurant.shared.utility.DayTimings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Arrays;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCommandModel {
    @Id
    private String id;
    private String name;
    private Double value;
    private DescriptionCommandModel descriptionCommandModel;
    private Float rating;

    public ItemCommandModel(ItemRequest itemRequest) {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.name = itemRequest.getName();
        this.value = itemRequest.getValue();
        this.descriptionCommandModel = new DescriptionCommandModel();
        this.descriptionCommandModel.setComment(itemRequest.getComment());
        this.descriptionCommandModel.setIngredient(itemRequest.getIngredient());
        this.descriptionCommandModel.setTimingsCommandModelList(Arrays.asList(
                new TimingsCommandModel(DayTimings.MORNING, itemRequest.getStartFirst(), itemRequest.getEndFirst()),
                new TimingsCommandModel(DayTimings.EVENING, itemRequest.getStartSecond(), itemRequest.getEndSecond())
        ));
        this.rating = 0.0f;
    }
}
