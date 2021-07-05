package com.foodgrid.common.event.service;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.skeleton.event.handler.AuthenticationEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationEventHandlerImplementation implements AuthenticationEventHandler {

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationEventHandlerImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void authConsumer(AuthenticationEvent event) {
        if (Boolean.TRUE.equals(event.getUpdated())) {
            for (UserAuthEventDTO userAuthEventDTO : event.getUsers()) {
                switch (userAuthEventDTO.getActivity()) {
                    case LOGIN:
                        login(userAuthEventDTO.getToken(), userAuthEventDTO.getUser());
                        break;
                    case SIGNUP:
                        signup(userAuthEventDTO.getUser());
                        break;
                    case LOGOUT:
                        logout(userAuthEventDTO.getToken(), userAuthEventDTO.getUser());
                        break;
                    case LOGOUT_ALL:
                        logoutAll(userAuthEventDTO.getUser());
                        break;
                    case DELETE:
                        delete(userAuthEventDTO.getUser());
                        break;
                    case PATCH:
                    case CHANGE_PASSWORD:
                        patch(userAuthEventDTO.getUser());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void patch(User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(userRepository::save);
    }

    @Override
    public void delete(User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(userRepository::delete);
    }

    @Override
    public void logout(String token, User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(user1 -> userRepository.save(user1.removeToken(token)));
    }

    @Override
    public void login(String token, User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(user1 -> userRepository.save(user1.addToken(token)));
    }

    @Override
    public void logoutAll(User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(user1 -> userRepository.save(user1.setActiveTokens(new ArrayList<>())));
    }

    @Override
    public void signup(User user) {
        if (Boolean.FALSE.equals(userRepository.existsByUsername(user.getUsername())))
            userRepository.save(user);
    }
}
