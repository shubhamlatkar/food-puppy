package com.foodgrid.restaurant.command.internal.service;

import com.foodgrid.common.payload.dto.response.GenericIdResponse;
import com.foodgrid.common.security.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RestaurantAuthenticationService {

    private final AuthenticationService authenticationService;
    private final MenuCommandService menuCommandService;

    @Autowired
    public RestaurantAuthenticationService(AuthenticationService authenticationService, MenuCommandService menuCommandService) {
        this.authenticationService = authenticationService;
        this.menuCommandService = menuCommandService;
    }

    public GenericIdResponse deleteMe() {
        log.info("Menu Command remove invoked through delete user");
        menuCommandService.removeMenu();
        return authenticationService.delete();
    }
}
