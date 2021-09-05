package com.foodgrid.user.query;

import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.user.command.internal.payload.dto.request.AddressRequest;
import com.foodgrid.user.query.internal.model.aggregate.AddressQueryModel;
import com.foodgrid.user.query.internal.payload.response.FindByUserId;
import com.foodgrid.user.query.internal.rest.AddressQueryController;
import com.foodgrid.user.query.internal.service.AddressQueryService;
import com.foodgrid.user.shared.model.Location;
import com.foodgrid.user.shared.payload.AddressEventDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AddressQueryController.class})
@AutoConfigureWebTestClient
class AddressQueryControllerTests {
    @MockBean
    private AddressQueryService addressService;

    @Autowired
    private AddressQueryController addressQueryController;

    @Test
    @DisplayName("Tests addAddress method of AddressQueryControllerTests")
    void addAddress() {
        when(addressService.getAddressByUserId("1")).thenReturn(new FindByUserId("1", null));
        Assertions.assertNotNull(addressQueryController.getAddressById("1"));
    }

    @Test
    @DisplayName("Tests getAddressById method of AddressQueryControllerTests")
    void getAddressById() {
        var address = new AddressRequest(new Location(12.1, 31.2), "home", "test line 1", "test line 2", "313131", "pune", "MH", true);
        when(addressService.getAddressById("1")).thenReturn(new AddressQueryModel(new AddressEventDto(address, "1", "1", CrudActions.ADD)));
        Assertions.assertNotNull(addressQueryController.getAddressById("1"));
    }
}
