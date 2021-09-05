package com.foodgrid.user.command;

import com.foodgrid.user.command.internal.model.aggregate.AddressCommandModel;
import com.foodgrid.user.command.internal.model.aggregate.CartCommandModel;
import com.foodgrid.user.command.internal.model.entity.ItemCommandModel;
import com.foodgrid.user.shared.model.AddressDetails;
import com.foodgrid.user.shared.model.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest(classes = {AddressCommandModel.class})
@AutoConfigureWebTestClient
class CommandAggregateTests {

    @Test
    @DisplayName("Tests AddressCommandModel ")
    void addressCommandModel() {
        var address = new AddressCommandModel("1", new Location(), "test", new AddressDetails(), true);
        Assertions.assertTrue(address.getIsSelected());
    }

    @Test
    @DisplayName("Tests CartCommandModel ")
    void cartCommandModel() {
        var cart1 = new CartCommandModel("1", "1restaurantId", Set.of(new ItemCommandModel("1", "test", 12.1, 1)));
        var cart2 = new CartCommandModel("2", "restaurant2");
        cart2.addItem(new ItemCommandModel("1", "test", 12.1, 1));
        cart2.removeItem("1");
        Assertions.assertNotNull(cart2.decreaseQuantity("1"));
        Assertions.assertNotNull(cart1.getUserId());
    }

}
