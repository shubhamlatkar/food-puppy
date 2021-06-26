package com.foodgrid.common.security.service;

import com.foodgrid.common.security.implementation.UserDetailsImplementation;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.payload.dto.request.LogIn;
import com.foodgrid.common.security.payload.dto.request.SignUp;
import com.foodgrid.common.security.payload.dto.response.JwtResponse;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.security.utility.JwtTokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
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

    public ResponseEntity<String> signup(SignUp signUp, BindingResult result) {
        return (userDetailsService.saveUser(signUp) && !result.hasErrors()) ? ResponseEntity.ok().body("Saved") : ResponseEntity.badRequest().body("Bad Credentials");
    }

    public ResponseEntity<String> logOut() {
        return ResponseEntity.ok().body("logged out....");
    }

    public ResponseEntity<String> logoutAll() {
        return ResponseEntity.ok("Logged out from all devices");
    }

    public ResponseEntity<Object> login(LogIn request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Bad cred");
        }

        final UserDetailsImplementation userDetails = (UserDetailsImplementation) userDetailsService.loadUserByUsername(
                request.getUsername()
        );

        final String jwtToken = jwtTokenUtility.generateToken(userDetails);
        
        userRepository.findByUsername(request.getUsername()).ifPresent(user -> userRepository.save(user.addToken(jwtToken)));

        return ResponseEntity.ok(new JwtResponse(jwtToken, userDetails.getId(), userDetails.getUsername()));
    }
}
