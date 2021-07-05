package com.foodgrid.common.skeleton.event.handler;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.security.model.aggregate.User;

public interface AuthenticationEventHandler {

    void authConsumer(AuthenticationEvent event);

    void patch(User user);

    void delete(User user);

    void logout(String jwt, User user);

    void login(String jwt, User user);

    void logoutAll(User user);

    void signup(User user);
}
