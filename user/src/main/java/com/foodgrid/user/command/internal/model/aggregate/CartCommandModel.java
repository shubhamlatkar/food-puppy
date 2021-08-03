package com.foodgrid.user.command.internal.model.aggregate;

import com.foodgrid.user.command.internal.model.entity.ItemCommandModel;
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
public class CartCommandModel {
    @Id
    private String userId;
    private String restaurantId;
    private Set<ItemCommandModel> items;

    public CartCommandModel(String userId, String restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public CartCommandModel addItem(ItemCommandModel item) {
        if (items == null)
            items = new HashSet<>();
        items.add(item);
        return this;
    }

    public CartCommandModel removeItem(String itemId) {
        if (items != null)
            items.removeIf(item1 -> item1.getId().equals(itemId));
        return this;
    }

    public CartCommandModel increaseQuantity(String itemId) {
        if (items != null)
            items.forEach(item -> {
                if (item.getId().equals(itemId))
                    item.setQuantity(item.getQuantity() + 1);
            });
        return this;
    }

    public CartCommandModel decreaseQuantity(String itemId) {
        if (items != null)
            items.forEach(item -> {
                if (item.getId().equals(itemId)) {
                    if (item.getQuantity() == 1)
                        items.remove(item);
                    else
                        item.setQuantity(item.getQuantity() - 1);
                }
            });
        return this;
    }
}
