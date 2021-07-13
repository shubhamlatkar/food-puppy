package com.foodgrid.common.event.service;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.skeleton.event.handler.AuthenticationEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationEventHandlerImplementation implements AuthenticationEventHandler {

    private final UserRepository userRepository;
    private final UserDetailsServiceImplementation userDetailsServiceImplementation;

    @Autowired
    public AuthenticationEventHandlerImplementation(UserRepository userRepository, UserDetailsServiceImplementation userDetailsServiceImplementation) {
        this.userRepository = userRepository;
        this.userDetailsServiceImplementation = userDetailsServiceImplementation;
    }

    @Override
    public void authConsumer(AuthenticationEvent event) {
        if (Boolean.TRUE.equals(event.getIsUpdated())) {
            for (UserAuthEventDTO userAuthEventDTO : event.getUsers()) {
                switch (userAuthEventDTO.getActivity()) {
                    case LOGIN:
                        login(userAuthEventDTO);
                        break;
                    case SIGNUP:
                        signup(userAuthEventDTO);
                        break;
                    case LOGOUT:
                        logout(userAuthEventDTO);
                        break;
                    case LOGOUT_ALL:
                        logoutAll(userAuthEventDTO);
                        break;
                    case DELETE:
                        delete(userAuthEventDTO);
                        break;
                    case PATCH:
                    case CHANGE_PASSWORD:
                        patch(userAuthEventDTO);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void patch(UserAuthEventDTO user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(user1 -> {
            user1.setUsername(user.getUsername());
            user1.setPassword(user.getPassword());
            userRepository.save(user1);
        });
    }

    @Override
    public void delete(UserAuthEventDTO user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(userRepository::delete);
    }

    @Override
    public void logout(UserAuthEventDTO user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(user1 -> userRepository.save(user1.removeToken(user.getToken())));
    }

    @Override
    public void login(UserAuthEventDTO user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(user1 -> userRepository.save(user1.addToken(user.getToken())));
    }

    @Override
    public void logoutAll(UserAuthEventDTO user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(user1 -> userRepository.save(user1.setActiveTokens(new ArrayList<>())));
    }

    @Override
    public void signup(UserAuthEventDTO user) {
        if (Boolean.FALSE.equals(userRepository.existsByUsername(user.getUsername()))) {
            Set<String> roles = new HashSet<>();
            roles.add(user.getRole());
            userDetailsServiceImplementation.saveUser(new SignUp(user.getUsername(), null, roles, user.getPassword(), null, user.getUserType()));
        }
    }
}
