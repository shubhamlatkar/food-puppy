package com.foodgrid.user.query;

import com.foodgrid.user.query.internal.model.aggregate.AddressQueryModel;
import com.foodgrid.user.query.internal.repository.AddressQueryRepository;
import com.foodgrid.user.query.internal.service.AddressQueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

@SpringBootTest(classes = {AddressQueryService.class})
@AutoConfigureWebTestClient
class AddressQueryServiceTests {

    @MockBean
    private AddressQueryRepository addressQueryRepository;

    @Autowired
    private AddressQueryService addressQueryService;

    @Test
    @DisplayName("Tests deleteAddressById method of AddressQueryService")
    void deleteAddressById() {
        doAnswer(invocationOnMock -> null)
                .when(addressQueryRepository).deleteById(any());
        Assertions.assertNotNull(addressQueryService.deleteAddressById("1"));
    }

    @Test
    @DisplayName("Tests patchAddress method of AddressQueryService")
    void patchAddress() {
        doAnswer(invocationOnMock -> null)
                .when(addressQueryRepository).save(any());
        Assertions.assertNotNull(addressQueryService.patchAddress(new AddressQueryModel("1", "1", null, "1", null, true)));
    }

    @Test
    @DisplayName("Tests getAddressByUserId method of AddressQueryService")
    void getAddressByUserId() {
        doAnswer(invocationOnMock -> java.util.Optional.of(List.of(new AddressQueryModel("1", "1", null, "1", null, true))))
                .when(addressQueryRepository).findByUserId(anyString());
        Assertions.assertNotNull(addressQueryService.getAddressByUserId("1"));
    }

    @Test
    @DisplayName("Tests getAddressById method of AddressQueryService")
    void getAddressById() {
        doAnswer(invocationOnMock -> java.util.Optional.of(new AddressQueryModel("1", "1", null, "1", null, true)))
                .when(addressQueryRepository).findById(any());
        Assertions.assertNotNull(addressQueryService.getAddressById("1"));
    }


}
