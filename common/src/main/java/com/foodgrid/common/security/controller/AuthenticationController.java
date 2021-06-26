package com.foodgrid.common.security.controller;

import com.foodgrid.common.security.payload.dto.request.LogIn;
import com.foodgrid.common.security.payload.dto.request.SignUp;
import com.foodgrid.common.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@Valid @RequestBody SignUp signupRequest, BindingResult result) {
        try {
            return authenticationService.signup(signupRequest, result);
        } catch (Exception e) {
            return new ResponseEntity<>("Some exception caught", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tryAutoLogin")
    public ResponseEntity<Object> tryAutoLogin() {
        return ResponseEntity.ok().body("Authenticated");
    }

    @GetMapping("/logmeout")
    public ResponseEntity<String> logout() {
        return authenticationService.logOut();
    }

    @GetMapping("/logoutall")
    public ResponseEntity<String> logoutAll() {
        return authenticationService.logoutAll();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> getJwtToken(@RequestBody LogIn request) {
        try {
            return authenticationService.login(request);
        } catch (Exception e) {
            return new ResponseEntity<>("Some exception caught", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
