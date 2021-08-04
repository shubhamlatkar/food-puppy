package com.foodgrid.user.shared.payload;

import com.foodgrid.user.command.internal.model.entity.ItemCommandModel;
import com.foodgrid.user.shared.utility.CartActivities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartEventDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private CartActivities activity;
    private String userId;
    private String restaurantId;
    private String itemId;
    private String name;
    private Double value;
    private Integer quantity;

    public CartEventDTO(CartActivities activity, ItemCommandModel item, String userId, String restaurantId) {
        this.userId = userId;
        this.activity = activity;
        this.restaurantId = restaurantId;
        this.itemId = item.getId();
        this.name = item.getName();
        this.value = item.getValue();
        this.quantity = item.getQuantity();
    }
}
