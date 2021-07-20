package com.foodgrid.common.event.service;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.payload.dto.event.UserAuthEventDTO;

public interface AuthenticationEventHandler {

    void authConsumer(AuthenticationEvent event);

    void patch(UserAuthEventDTO user);

    void delete(UserAuthEventDTO user);

    void logout(UserAuthEventDTO user);

    void login(UserAuthEventDTO user);

    void logoutAll(UserAuthEventDTO user);

    void signup(UserAuthEventDTO user);
}
