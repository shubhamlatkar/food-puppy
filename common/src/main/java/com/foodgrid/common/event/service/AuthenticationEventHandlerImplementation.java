package com.foodgrid.common.event.service;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.payload.logger.InformationLog;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.repository.RoleRepository;
import com.foodgrid.common.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class AuthenticationEventHandlerImplementation implements AuthenticationEventHandler {

    private final UserRepository userRepository;
    private final UserDetailsServiceImplementation userDetailsServiceImplementation;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationEventHandlerImplementation(UserRepository userRepository, UserDetailsServiceImplementation userDetailsServiceImplementation, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userDetailsServiceImplementation = userDetailsServiceImplementation;
        this.roleRepository = roleRepository;
    }

    private Boolean rolesExist() {
        return roleRepository.findByName("USER").isPresent();
    }

    @Override
    public void authConsumer(AuthenticationEvent event) {
        if (Boolean.TRUE.equals(event.getIsUpdated()) && Boolean.TRUE.equals(rolesExist())) {
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
        } else if (Boolean.FALSE.equals(rolesExist())) {
            authConsumer(event);
        }
    }

    @Override
    public void patch(UserAuthEventDTO user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(user1 -> {
            if (user1.getMetadata().getLastActivity() != user.getActivity() || user1.getMetadata().getLastUpdatedAt().getTime() < (new Date().getTime() - 10000)) {
                user1.setUsername(user.getUsername());
                user1.setPassword(user.getPassword());
                userRepository.save(user1);
                log.info(
                        new InformationLog(
                                this.getClass().getName(),
                                "patch",
                                user1.getType() + " user patched " + user1
                        ).toString()
                );
            }
        });
    }

    @Override
    public void delete(UserAuthEventDTO user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(user1 -> {
            if (user1.getMetadata().getLastActivity() != user.getActivity() || user1.getMetadata().getLastUpdatedAt().getTime() < (new Date().getTime() - 10000)) {
                userRepository.delete(user1);
                log.info(
                        new InformationLog(
                                this.getClass().getName(),
                                "delete",
                                user1.getType().name() + " user delete " + user
                        ).toString()
                );
            }
        });
    }

    @Override
    public void logout(UserAuthEventDTO user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(user1 -> {
            if (user1.getMetadata().getLastActivity() != user.getActivity() || user1.getMetadata().getLastUpdatedAt().getTime() < (new Date().getTime() - 10000)) {
                userRepository.save(user1.removeToken(user.getToken()));
                log.info(
                        new InformationLog(
                                this.getClass().getName(),
                                "logout",
                                user1.getType().name() + " user logged out " + user
                        ).toString()
                );
            }
        });
    }

    @Override
    public void login(UserAuthEventDTO user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(user1 -> {
            if (user1.getMetadata().getLastActivity() != user.getActivity() || user1.getMetadata().getLastUpdatedAt().getTime() < (new Date().getTime() - 10000)) {
                userRepository.save(user1.addToken(user.getToken()));
                log.info(
                        new InformationLog(
                                this.getClass().getName(),
                                "login",
                                user1.getType().name() + " user login " + user
                        ).toString()
                );
            }
        });
    }

    @Override
    public void logoutAll(UserAuthEventDTO user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(user1 -> {
            if (user1.getMetadata().getLastActivity() != user.getActivity() || user1.getMetadata().getLastUpdatedAt().getTime() < (new Date().getTime() - 10000)) {
                userRepository.save(user1.setActiveTokens(new ArrayList<>()));
                log.info(
                        new InformationLog(
                                this.getClass().getName(),
                                "logoutAll",
                                user1.getType().name() + " user logged out from all devices " + user
                        ).toString()
                );
            }
        });
    }

    @Override
    public void signup(UserAuthEventDTO user) {
        if (Boolean.FALSE.equals(userRepository.existsByUsername(user.getUsername()))) {
            Set<String> roles = new HashSet<>();
            roles.add("ROLE_" + user.getRole());
            userDetailsServiceImplementation.saveUser(new SignUp(user.getUsername(), user.getEmail(), roles, user.getPassword(), user.getPhone(), user.getUserType()));
            log.info(
                    new InformationLog(
                            this.getClass().getName(),
                            "signup",
                            user.getUserType().name() + " user signup " + user
                    ).toString()
            );
        }
    }
}
