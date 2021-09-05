package com.foodgrid.user.command;

import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.user.command.internal.event.broker.AddressEventBroker;
import com.foodgrid.user.command.internal.model.aggregate.AddressCommandModel;
import com.foodgrid.user.command.internal.payload.dto.request.AddressRequest;
import com.foodgrid.user.command.internal.repository.AddressCommandRepository;
import com.foodgrid.user.command.internal.service.AddressCommandService;
import com.foodgrid.user.shared.model.Location;
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
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AddressCommandService.class})
@AutoConfigureWebTestClient
class AddressCommandServiceTests {
    @MockBean
    private AddressCommandRepository addressRepository;
    @MockBean
    private AddressEventBroker addressEventBroker;
    @MockBean
    private UserSession userSession;

    @Autowired
    private AddressCommandService addressCommandService;

    @Test
    @DisplayName("Tests addAddress method of AddressCommandService")
    void addAddress() {
        var address = new AddressRequest(new Location(12.1, 31.2), "home", "test line 1", "test line 2", "313131", "pune", "MH", true);
        var existingAddress = new AddressCommandModel(address, "1");
        existingAddress.setId("1");
        var result = new BindingResults();
        when(addressRepository.findByUserId("1")).thenReturn(java.util.Optional.of(List.of(existingAddress)));
        when(userSession.getUserId()).thenReturn("1");
        doAnswer(invocationOnMock -> existingAddress)
                .when(addressRepository).save(any());
        doAnswer(invocationOnMock -> null)
                .when(addressEventBroker).sendAddressEvent(any());
        Assertions.assertNotNull(addressCommandService.addAddress(address, result));
    }

    @Test
    @DisplayName("Tests deleteAddressById method of AddressCommandService")
    void deleteAddressById() {
        when(userSession.getUserId()).thenReturn("1");
        doAnswer(invocationOnMock -> null)
                .when(addressRepository).deleteById(anyString());
        doAnswer(invocationOnMock -> null)
                .when(addressEventBroker).sendAddressEvent(any());
        Assertions.assertNotNull(addressCommandService.deleteAddressById("1"));
    }

    @Test
    @DisplayName("Tests patchAddress method of AddressCommandService")
    void patchAddress() {
        var address = new AddressRequest(new Location(12.1, 31.2), "home", "test line 1", "test line 2", "313131", "pune", "MH", true);
        var existingAddress = new AddressCommandModel(address, "1");
        existingAddress.setId("1");
        var result = new BindingResults();
        when(addressRepository.findById("1")).thenReturn(java.util.Optional.of(existingAddress));
        when(userSession.getUserId()).thenReturn("1");
        doAnswer(invocationOnMock -> null)
                .when(addressRepository).save(any());
        doAnswer(invocationOnMock -> null)
                .when(addressEventBroker).sendAddressEvent(any());
        Assertions.assertNotNull(addressCommandService.patchAddress("1", address, result));
    }

}
