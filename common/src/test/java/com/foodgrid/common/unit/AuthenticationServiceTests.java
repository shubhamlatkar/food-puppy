package com.foodgrid.common.unit;

import com.foodgrid.common.payload.dto.request.LogIn;
import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.security.component.DeletedUsers;
import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.security.implementation.UserDetailsImplementation;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.model.aggregate.Authority;
import com.foodgrid.common.security.model.aggregate.Role;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.model.entity.UserMetadata;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.security.service.AuthenticationService;
import com.foodgrid.common.security.utility.JwtTokenUtility;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.beans.PropertyEditor;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AuthenticationService.class})
@AutoConfigureWebTestClient
class AuthenticationServiceTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserDetailsServiceImplementation userDetailsService;

    @MockBean
    private JwtTokenUtility jwtTokenUtility;

    @MockBean
    private UserSession userSession;

    @MockBean
    private DeletedUsers deletedUsers;

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    void testUserDetailsServiceImplementationTestsSignUp() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        var signUp = new SignUp(
                "rosewood",
                "rosewood@shu.com",
                Set.of("ROLE_USER"),
                "rosewood@shu.com",
                "1234567890",
                UserTypes.USER
        );
        when(userDetailsService.saveUser(signUp))
                .thenReturn(Boolean.TRUE);

        var result = new BindingResults();

        Assertions.assertNotNull(authenticationService.signup(signUp, result));

    }

    @Test
    void testUserDetailsServiceImplementationTestsLogOut() {
        Assertions.assertNotNull(authenticationService.logOut());
    }

    @Test
    void testUserDetailsServiceImplementationTestsLogoutAll() {
        Assertions.assertNotNull(authenticationService.logoutAll());
    }

    @Test
    void testUserDetailsServiceImplementationTestsLogin() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        doAnswer(invocationOnMock -> null)
                .when(authenticationManager).authenticate(any());

        doAnswer(invocationOnMock -> UserDetailsImplementation.build(tempUser))
                .when(userDetailsService).loadUserByUsername(any());

        when(userRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(tempUser));

        Assertions.assertNotNull(authenticationService.login(new LogIn("test", "test")));
    }

    @Test
    void testUserDetailsServiceImplementationTestsTryAutoLogin() {
        Assertions.assertNotNull(authenticationService.tryAutoLogin());
    }

    @Test
    void testUserDetailsServiceImplementationTestsException() {
        Assertions.assertNotNull(authenticationService.exception());
    }

    @Test
    void testUserDetailsServiceImplementationTestsDelete() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        when(userRepository.findById(anyString())).thenReturn(java.util.Optional.of(tempUser));
        doAnswer(invocationOnMock -> null)
                .when(userRepository).delete(any());
        doAnswer(invocationOnMock -> null)
                .when(deletedUsers).addUser(any());
        doAnswer(invocationOnMock -> "1")
                .when(userSession).getUserId();
        Assertions.assertNotNull(authenticationService.delete());
    }
}

class BindingResults implements BindingResult {

    @Override
    public Object getTarget() {
        return null;
    }

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
