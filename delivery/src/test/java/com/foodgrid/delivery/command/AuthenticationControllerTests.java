package com.foodgrid.delivery.command;

import com.foodgrid.common.exception.model.ApiExceptionDTO;
import com.foodgrid.common.payload.dto.request.LogIn;
import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.payload.dto.response.AuthenticationActionResponse;
import com.foodgrid.common.payload.dto.response.JwtResponse;
import com.foodgrid.common.security.service.AuthenticationService;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import com.foodgrid.delivery.command.internal.payload.dto.DeliverySignUp;
import com.foodgrid.delivery.command.internal.rest.AuthenticationController;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.beans.PropertyEditor;
import java.util.*;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AuthenticationController.class})
@AutoConfigureWebTestClient
class AuthenticationControllerTests {
    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationController authenticationController;

    @Test
    @DisplayName("Tests signup method of AuthenticationController controller")
    void signupUser() {
        var signUp = new SignUp("test", "test@test.com", new HashSet<>(Collections.singletonList("ROLE_USER")), "test", "1234567890", UserTypes.USER);
        var result = new BindingResults();
        var signUpRequest = new DeliverySignUp("test", "test@test.com", "test", "1234567890");
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
}

class BindingResults implements BindingResult {

    @Override
    public Object getTarget() {
        return null;
    }

    @NotNull
    @Override
    public Map<String, Object> getModel() {
        return null;
    }

    @Override
    public Object getRawFieldValue(String s) {
        return null;
    }

    @Override
    public PropertyEditor findEditor(String s, Class<?> aClass) {
        return null;
    }

    @Override
    public PropertyEditorRegistry getPropertyEditorRegistry() {
        return null;
    }

    @Override
    public String[] resolveMessageCodes(String s) {
        return new String[0];
    }

    @Override
    public String[] resolveMessageCodes(String s, String s1) {
        return new String[0];
    }

    @Override
    public void addError(ObjectError objectError) {

    }

    @Override
    public String getObjectName() {
        return null;
    }

    @Override
    public void setNestedPath(String s) {

    }

    @Override
    public String getNestedPath() {
        return null;
    }

    @Override
    public void pushNestedPath(String s) {

    }

    @Override
    public void popNestedPath() throws IllegalStateException {

    }

    @Override
    public void reject(String s) {

    }

    @Override
    public void reject(String s, String s1) {

    }

    @Override
    public void reject(String s, Object[] objects, String s1) {

    }

    @Override
    public void rejectValue(String s, String s1) {

    }

    @Override
    public void rejectValue(String s, String s1, String s2) {

    }

    @Override
    public void rejectValue(String s, String s1, Object[] objects, String s2) {

    }

    @Override
    public void addAllErrors(Errors errors) {

    }

    @Override
    public boolean hasErrors() {
        return false;
    }

    @Override
    public int getErrorCount() {
        return 0;
    }

    @Override
    public List<ObjectError> getAllErrors() {
        return null;
    }

    @Override
    public boolean hasGlobalErrors() {
        return false;
    }

    @Override
    public int getGlobalErrorCount() {
        return 0;
    }

    @Override
    public List<ObjectError> getGlobalErrors() {
        return null;
    }

    @Override
    public ObjectError getGlobalError() {
        return null;
    }

    @Override
    public boolean hasFieldErrors() {
        return false;
    }

    @Override
    public int getFieldErrorCount() {
        return 0;
    }

    @Override
    public List<FieldError> getFieldErrors() {
        return null;
    }

    @Override
    public FieldError getFieldError() {
        return null;
    }

    @Override
    public boolean hasFieldErrors(String s) {
        return false;
    }

    @Override
    public int getFieldErrorCount(String s) {
        return 0;
    }

    @Override
    public List<FieldError> getFieldErrors(String s) {
        return null;
    }

    @Override
    public FieldError getFieldError(String s) {
        return null;
    }

    @Override
    public Object getFieldValue(String s) {
        return null;
    }

    @Override
    public Class<?> getFieldType(String s) {
        return null;
    }
}