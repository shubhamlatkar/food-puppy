package com.foodgrid.user.command;

import com.foodgrid.user.command.internal.payload.dto.request.AddressRequest;
import com.foodgrid.user.command.internal.payload.dto.response.AddressOperationSuccess;
import com.foodgrid.user.command.internal.rest.AddressCommandController;
import com.foodgrid.user.command.internal.service.AddressCommandService;
import com.foodgrid.user.shared.model.Location;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.beans.PropertyEditor;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AddressCommandController.class})
@AutoConfigureWebTestClient
class AddressCommandControllerTests {

    @MockBean
    private AddressCommandService addressCommandService;

    @Autowired
    private AddressCommandController addressCommandController;

    @Test
    void deleteAddressById() {
        when(addressCommandService.deleteAddressById(anyString())).thenReturn(new AddressOperationSuccess("1", "deleted..."));
        Assertions.assertNotNull(addressCommandController.deleteAddressById("test"));
    }

    @Test
    void addAddress() {
        var address = new AddressRequest(new Location(12.1, 31.2), "home", "test line 1", "test line 2", "313131", "pune", "MH", true);
        var result = new BindingResults();
        when(addressCommandService.addAddress(address, result)).thenReturn(new AddressOperationSuccess("1", "added..."));
        Assertions.assertNotNull(addressCommandController.addAddress(address, result));
    }

    @Test
    void patchAddress() {
        var address = new AddressRequest(new Location(12.1, 31.2), "home", "test line 1", "test line 2", "313131", "pune", "MH", true);
        var result = new BindingResults();
        when(addressCommandService.patchAddress("1", address, result)).thenReturn(new AddressOperationSuccess("1", "added..."));
        Assertions.assertNotNull(addressCommandController.patchAddress("1", address, result));
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
