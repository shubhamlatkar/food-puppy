package com.foodgrid.user.command.internal.model.aggregate;

import com.foodgrid.user.command.internal.model.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class CartCommandModel {
    @Id
    private String id;
    private String userId;
    private List<Item> items;

    public CartCommandModel addItem(Item item) {
        if (items == null)
            items = new ArrayList<>();
        items.add(item);
        return this;
    }

    public CartCommandModel removeItem(Item item) {
        if (items != null)
            items.removeIf(item1 -> item1.getId().equals(item.getId()));
        return this;
    }
}
