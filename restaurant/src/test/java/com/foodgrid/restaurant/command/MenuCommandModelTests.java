package com.foodgrid.restaurant.command;

import com.foodgrid.restaurant.command.internal.model.aggregate.MenuCommandModel;
import com.foodgrid.restaurant.command.internal.model.entity.ItemCommandModel;
import com.foodgrid.restaurant.command.internal.payload.dto.request.ItemRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MenuCommandModel.class})
@AutoConfigureWebTestClient
class MenuCommandModelTests {

    @Test
    @DisplayName("Tests patch method of MenuCommandModel")
    void patchItem() {
        var item = new ItemCommandModel("1", "test", 12.12, null, 4.3f);
        var menu = new MenuCommandModel("1", "1", null);
        menu.addItem(item);
        Assertions.assertNotNull(menu.patchItem(new ItemRequest("testNameTets", 12.23, "testIngredientsChanged", "testCommentsChanged", 9, 14, 19, 23), "1"));
    }
}
