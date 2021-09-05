package com.foodgrid.user.command;

import com.foodgrid.common.payload.dto.response.GenericIdResponse;
import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.security.service.AuthenticationService;
import com.foodgrid.user.command.internal.model.aggregate.AddressCommandModel;
import com.foodgrid.user.command.internal.payload.dto.request.AddressRequest;
import com.foodgrid.user.command.internal.repository.AddressCommandRepository;
import com.foodgrid.user.command.internal.service.AddressCommandService;
import com.foodgrid.user.command.internal.service.CartCommandService;
import com.foodgrid.user.command.internal.service.UserAuthenticationService;
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
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserAuthenticationService.class})
@AutoConfigureWebTestClient
class UserAuthenticationServiceTests {

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private AddressCommandRepository addressCommandRepository;
    @MockBean
    private UserSession userSession;
    @MockBean
    private AddressCommandService addressCommandService;
    @MockBean
    private CartCommandService cartCommandService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Test
    @DisplayName("Tests deleteMe method of UserAuthenticationService")
    void deleteMe() {
        var address = new AddressRequest(new Location(12.1, 31.2), "home", "test line 1", "test line 2", "313131", "pune", "MH", true);
        var existingAddress = new AddressCommandModel(address, "1");
        when(userSession.getUserId()).thenReturn("1");
        doAnswer(invocationOnMock -> java.util.Optional.of(List.of(existingAddress)))
                .when(addressCommandRepository).findByUserId(any());
        doAnswer(invocationOnMock -> null)
                .when(cartCommandService).removeCart();
        doAnswer(invocationOnMock -> new GenericIdResponse(userSession.getUserId(), "Deleted successfully.."))
                .when(authenticationService).delete();
        Assertions.assertNotNull(userAuthenticationService.deleteMe());
    }
}
