package com.foodgrid.user.query.internal.model.aggregate;

import com.foodgrid.user.query.internal.model.entity.ItemQueryModel;
import com.foodgrid.user.shared.payload.CartEventDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class CartQueryModel {
    @Id
    private String userId;
    private String restaurantId;
    private Set<ItemQueryModel> items;

    public CartQueryModel(String userId, String restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public CartQueryModel addItem(CartEventDTO cart) {
        if (items == null)
            items = new HashSet<>();
        items.add(new ItemQueryModel(cart.getItemId(), cart.getName(), cart.getValue(), cart.getQuantity()));
        return this;
    }

    public CartQueryModel removeItem(String itemId) {
        if (Boolean.FALSE.equals(items.isEmpty()))
            items.removeIf(itemQueryModel -> itemQueryModel.getId().equals(itemId));
        return this;
    }

    public CartQueryModel increaseQuantity(String itemId) {
        if (Boolean.FALSE.equals(items.isEmpty()))
            items.forEach(itemQueryModel -> {
                if (itemQueryModel.getId().equals(itemId))
                    itemQueryModel.setQuantity(itemQueryModel.getQuantity() + 1);
            });
        return this;
    }

    public CartQueryModel decreaseQuantity(String itemId) {
        if (Boolean.FALSE.equals(items.isEmpty()))
            items.forEach(itemQueryModel -> {
                if (itemQueryModel.getId().equals(itemId))
                    itemQueryModel.setQuantity(itemQueryModel.getQuantity() - 1);
            });
        return this;
    }
}
