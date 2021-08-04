package com.foodgrid.user.command.internal.service;

import com.foodgrid.common.payload.dto.response.GenericIdResponse;
import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.security.service.AuthenticationService;
import com.foodgrid.user.command.internal.repository.AddressCommandRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserAuthenticationService {

    private final AuthenticationService authenticationService;
    private final AddressCommandRepository addressCommandRepository;
    private final UserSession userSession;
    private final AddressCommandService addressCommandService;
    private final CartCommandService cartCommandService;

    @Autowired
    public UserAuthenticationService(AuthenticationService authenticationService, AddressCommandRepository addressCommandRepository, UserSession userSession, AddressCommandService addressCommandService, CartCommandService cartCommandService) {
        this.authenticationService = authenticationService;
        this.addressCommandRepository = addressCommandRepository;
        this.userSession = userSession;
        this.addressCommandService = addressCommandService;
        this.cartCommandService = cartCommandService;
    }

    public GenericIdResponse deleteMe() {
        log.info("Deleted address and user for id: {}", userSession.getUserId());
        addressCommandRepository.findByUserId(userSession.getUserId())
                .ifPresent(addressCommandModels ->
                        addressCommandModels
                                .parallelStream()
                                .forEach(addressCommandModel ->
                                        addressCommandService.deleteAddressById(addressCommandModel.getId())));
        cartCommandService.removeCart();
        return authenticationService.delete();
    }
}
