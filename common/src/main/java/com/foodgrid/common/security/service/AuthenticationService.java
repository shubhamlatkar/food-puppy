package com.foodgrid.common.security.service;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.common.exception.exceptions.InvalidDataException;
import com.foodgrid.common.exception.model.ApiExceptionDTO;
import com.foodgrid.common.payload.dto.request.LogIn;
import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.payload.dto.response.AuthenticationActionResponse;
import com.foodgrid.common.payload.dto.response.JwtResponse;
import com.foodgrid.common.payload.logger.ExceptionLog;
import com.foodgrid.common.security.implementation.UserDetailsImplementation;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.security.utility.JwtTokenUtility;
import com.foodgrid.common.utility.UserActivities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, UserDetailsServiceImplementation userDetailsService, JwtTokenUtility jwtTokenUtility) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtility = jwtTokenUtility;
    }

    public ResponseEntity<AuthenticationActionResponse> signup(SignUp signUp, BindingResult result) {
        if (result.hasErrors())
            throw new InvalidDataException("Invalid data");
        else if (Boolean.TRUE.equals(userDetailsService.saveUser(signUp)))
            return ResponseEntity.ok().body(
                    new AuthenticationActionResponse(
                            UserActivities.SIGNUP,
                            true,
                            new Date(),
                            "User data saved"
                    )
            );
        else
            throw new InternalServerErrorException("Some internal error caught");
    }

    public ResponseEntity<AuthenticationActionResponse> logOut() {
        return ResponseEntity.ok().body(
                new AuthenticationActionResponse(
                        UserActivities.LOGOUT,
                        true,
                        new Date(),
                        "Logged out successfully..."
                )
        );
    }

    public ResponseEntity<AuthenticationActionResponse> logoutAll() {
        return ResponseEntity.ok(
                new AuthenticationActionResponse(
                        UserActivities.LOGOUT_ALL,
                        true,
                        new Date(),
                        "Logged out from all devices...."
                )
        );
    }

    public ResponseEntity<JwtResponse> login(LogIn request) {
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

        return ResponseEntity.ok(new JwtResponse(jwtToken, userDetails.getUsername(), userDetails.getId()));
    }

    public ResponseEntity<AuthenticationActionResponse> tryAutoLogin() {
        return ResponseEntity.ok(
                new AuthenticationActionResponse(
                        UserActivities.LOGIN,
                        true,
                        new Date(),
                        "Refresh token successful..."
                )
        );
    }

    public ResponseEntity<ApiExceptionDTO> exception() {
        log.error(
                new ExceptionLog(
                        this.getClass().getName(),
                        "handleInvalidDataException",
                        "Invalid JWT Token",
                        HttpStatus.BAD_REQUEST
                ).toString()
        );
        return new ResponseEntity<>(
                new ApiExceptionDTO("Invalid JWT Token", HttpStatus.BAD_REQUEST, "Invalid data")
                , HttpStatus.BAD_REQUEST
        );
    }
}
