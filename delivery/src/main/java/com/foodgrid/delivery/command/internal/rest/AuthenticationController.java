package com.foodgrid.delivery.command.internal.rest;

import com.foodgrid.common.exception.model.ApiExceptionDTO;
import com.foodgrid.common.payload.dto.request.LogIn;
import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.payload.dto.response.AuthenticationActionResponse;
import com.foodgrid.common.payload.dto.response.JwtResponse;
import com.foodgrid.common.security.service.AuthenticationService;
import com.foodgrid.common.utility.UserTypes;
import com.foodgrid.delivery.command.internal.payload.dto.DeliverySignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/${endpoint.service}/${endpoint.version}")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PutMapping("/${endpoint.authentication.signup}")
    public ResponseEntity<AuthenticationActionResponse> signupUser(@Valid @RequestBody DeliverySignUp signupRequest, BindingResult result) {
        return ResponseEntity.ok(authenticationService.signup(
                new SignUp(
                        signupRequest.getUsername(),
                        signupRequest.getEmail(),
                        new HashSet<>(Collections.singletonList("ROLE_DELIVERY")),
                        signupRequest.getPassword(),
                        signupRequest.getPhone(),
                        UserTypes.DELIVERY
                ),
                result
        ));
    }

    @GetMapping("/${endpoint.authentication.autoLogin}")
    public ResponseEntity<AuthenticationActionResponse> tryAutoLogin() {
        return ResponseEntity.ok(authenticationService.tryAutoLogin());
    }

    @GetMapping("/${endpoint.authentication.logout}")
    public ResponseEntity<AuthenticationActionResponse> logout() {
        return ResponseEntity.ok(authenticationService.logOut());
    }

    @GetMapping("/${endpoint.authentication.logoutAll}")
    public ResponseEntity<AuthenticationActionResponse> logoutAll() {
        return ResponseEntity.ok(authenticationService.logoutAll());
    }

    @PostMapping("/${endpoint.authentication.login}")
    public ResponseEntity<JwtResponse> getJwtToken(@RequestBody LogIn request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping("/exception")
    public ResponseEntity<ApiExceptionDTO> exception() {
        return ResponseEntity.ok(authenticationService.exception());
    }

}