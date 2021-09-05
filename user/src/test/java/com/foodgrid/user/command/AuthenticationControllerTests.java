package com.foodgrid.user.command;

import com.foodgrid.common.exception.model.ApiExceptionDTO;
import com.foodgrid.common.payload.dto.request.LogIn;
import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.payload.dto.response.AuthenticationActionResponse;
import com.foodgrid.common.payload.dto.response.GenericIdResponse;
import com.foodgrid.common.payload.dto.response.JwtResponse;
import com.foodgrid.common.security.service.AuthenticationService;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import com.foodgrid.user.command.internal.payload.dto.request.UserSignUp;
import com.foodgrid.user.command.internal.rest.AuthenticationController;
import com.foodgrid.user.command.internal.service.UserAuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AuthenticationController.class})
@AutoConfigureWebTestClient
class AuthenticationControllerTests {

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private UserAuthenticationService userAuthenticationService;
    @Autowired
    private AuthenticationController authenticationController;

    @Test
    @DisplayName("Tests signup method of AuthenticationController controller")
    void signupUser() {
        var signUp = new SignUp("test", "test@test.com", new HashSet<>(Collections.singletonList("ROLE_USER")), "test", "1234567890", UserTypes.USER);
        var result = new BindingResults();
        var signUpRequest = new UserSignUp("test", "test@test.com", "test", "1234567890");
        when(authenticationService.signup(signUp, result)).thenReturn(new AuthenticationActionResponse(UserActivities.SIGNUP, true, new Date(), "Sign Up successful"));
        Assertions.assertNotNull(authenticationController.signupUser(signUpRequest, result));
    }

    @Test
    @DisplayName("Tests tryAutoLogin method of AuthenticationController controller")
    void tryAutoLogin() {
        when(authenticationService.tryAutoLogin()).thenReturn(new AuthenticationActionResponse(UserActivities.LOGIN, true, new Date(), "Login successful"));
        Assertions.assertNotNull(authenticationController.tryAutoLogin());
    }

    @Test
    @DisplayName("Tests logout method of AuthenticationController controller")
    void logout() {
        when(authenticationService.logOut()).thenReturn(new AuthenticationActionResponse(UserActivities.LOGOUT, true, new Date(), "Logout successful"));
        Assertions.assertNotNull(authenticationController.logout());
    }

    @Test
    @DisplayName("Tests logoutAll method of AuthenticationController controller")
    void logoutAll() {
        when(authenticationService.logoutAll()).thenReturn(new AuthenticationActionResponse(UserActivities.LOGOUT, true, new Date(), "logoutAll successful"));
        Assertions.assertNotNull(authenticationController.logoutAll());
    }

    @Test
    @DisplayName("Tests getJwtToken method of AuthenticationController controller")
    void getJwtToken() {
        var logInRequest = new LogIn("test", "test");
        when(authenticationService.login(logInRequest)).thenReturn(new JwtResponse("test Token", "test", "1"));
        Assertions.assertNotNull(authenticationController.getJwtToken(logInRequest));
    }

    @Test
    @DisplayName("Tests exception method of AuthenticationController controller")
    void exception() {
        when(authenticationService.exception()).thenReturn(new ApiExceptionDTO("Invalid JWT Token", HttpStatus.BAD_REQUEST, "Invalid data"));
        Assertions.assertNotNull(authenticationController.exception());
    }

    @Test
    @DisplayName("Tests deleteMe method of AuthenticationController controller")
    void deleteMe() {
        when(userAuthenticationService.deleteMe()).thenReturn(new GenericIdResponse("1", "Deleted successfully.."));
        Assertions.assertNotNull(authenticationController.deleteMe());
    }

}
