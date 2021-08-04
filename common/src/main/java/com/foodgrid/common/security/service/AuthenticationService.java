package com.foodgrid.common.security.service;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.common.exception.exceptions.InvalidDataException;
import com.foodgrid.common.exception.exceptions.NotFoundException;
import com.foodgrid.common.exception.model.ApiExceptionDTO;
import com.foodgrid.common.payload.dto.request.LogIn;
import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.payload.dto.response.AuthenticationActionResponse;
import com.foodgrid.common.payload.dto.response.GenericIdResponse;
import com.foodgrid.common.payload.dto.response.JwtResponse;
import com.foodgrid.common.security.component.DeletedUsers;
import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.security.implementation.UserDetailsImplementation;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.security.utility.JwtTokenUtility;
import com.foodgrid.common.utility.UserActivities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Date;

@Service
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImplementation userDetailsService;
    private final JwtTokenUtility jwtTokenUtility;
    private final UserSession userSession;
    private final DeletedUsers deletedUsers;

    @Autowired
    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, UserDetailsServiceImplementation userDetailsService, JwtTokenUtility jwtTokenUtility, UserSession userSession, DeletedUsers deletedUsers) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtility = jwtTokenUtility;
        this.userSession = userSession;
        this.deletedUsers = deletedUsers;
    }

    public AuthenticationActionResponse signup(SignUp signUp, BindingResult result) {
        if (result.hasErrors())
            throw new InvalidDataException("Invalid data");
        else if (Boolean.TRUE.equals(userDetailsService.saveUser(signUp))) {
            log.info("Signup invoked");
            return new AuthenticationActionResponse(
                    UserActivities.SIGNUP,
                    true,
                    new Date(),
                    "User data saved"
            );
        } else
            throw new InternalServerErrorException("Some internal error caught");
    }

    public AuthenticationActionResponse logOut() {
        log.info("Logger out...");
        return new AuthenticationActionResponse(
                UserActivities.LOGOUT,
                true,
                new Date(),
                "Logged out successfully..."
        );
    }

    public AuthenticationActionResponse logoutAll() {
        log.info("Logger out of all devices");
        return new AuthenticationActionResponse(
                UserActivities.LOGOUT_ALL,
                true,
                new Date(),
                "Logged out from all devices...."
        );
    }

    public JwtResponse login(LogIn request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new InvalidDataException(e.getMessage());
        }

        final UserDetailsImplementation userDetails = (UserDetailsImplementation) userDetailsService.loadUserByUsername(
                request.getUsername()
        );

        final String jwtToken = jwtTokenUtility.generateToken(userDetails);

        userRepository.findByUsername(request.getUsername()).ifPresent(user -> userRepository.save(user.addToken(jwtToken)));
        log.info("Login for user username: {}", request.getUsername());
        return new JwtResponse(jwtToken, userDetails.getUsername(), userDetails.getId());
    }

    public AuthenticationActionResponse tryAutoLogin() {
        log.info("Auto login");
        return new AuthenticationActionResponse(
                UserActivities.LOGIN,
                true,
                new Date(),
                "Refresh token successful..."
        );
    }

    public ApiExceptionDTO exception() {
        log.error("Invalid JWT Token");
        return new ApiExceptionDTO("Invalid JWT Token", HttpStatus.BAD_REQUEST, "Invalid data");
    }

    public GenericIdResponse delete() {
        userRepository.findById(userSession.getUserId()).ifPresentOrElse(
                user -> {
                    userRepository.delete(user);
                    user.deleteActivity();
                    deletedUsers.addUser(user);
                    log.info("Deleted user: {}", user.getUsername());
                },
                () -> {
                    throw new NotFoundException("User not found");
                }
        );
        return new GenericIdResponse(userSession.getUserId(), "Deleted successfully..");

    }
}
