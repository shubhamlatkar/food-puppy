package com.foodgrid.user.query;


import com.foodgrid.user.command.internal.model.entity.ItemCommandModel;
import com.foodgrid.user.query.internal.model.aggregate.CartQueryModel;
import com.foodgrid.user.query.internal.model.entity.ItemQueryModel;
import com.foodgrid.user.shared.payload.CartEventDTO;
import com.foodgrid.user.shared.utility.CartActivities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest(classes = {CartQueryModel.class})
@AutoConfigureWebTestClient
class QueryModelTests {
    @Test
    @DisplayName("Tests CartQueryModel ")
    void cartQueryModel() {
        var cart = new CartQueryModel("1", "1", Set.of(new ItemQueryModel("1", "test", 12.23, 1)));
        var cart2 = new CartQueryModel("2", "2");
        Assertions.assertNotNull(cart2.addItem(new CartEventDTO(CartActivities.ADD_ITEM, new ItemCommandModel("1", "test", 12.23, 1), "1", "1")));
        Assertions.assertNotNull(cart2.removeItem("1"));
        Assertions.assertNotNull(cart2.increaseQuantity("1"));
        Assertions.assertNotNull(cart2.decreaseQuantity("1"));
        Assertions.assertNotNull(cart.getItems());
    }
}
