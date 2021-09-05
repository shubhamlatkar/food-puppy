package com.foodgrid.common.unit;


import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.common.exception.exceptions.InvalidDataException;
import com.foodgrid.common.exception.exceptions.NotFoundException;
import com.foodgrid.common.exception.handler.ApiExceptionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.NoHandlerFoundException;

@SpringBootTest(classes = {ApiExceptionHandler.class})
@AutoConfigureWebTestClient
class ApiExceptionHandlerTests {
    @Test
    void testApiExceptionHandlerTestsHandleInvalidDataException() {
        var controllerAdvice = new ApiExceptionHandler();
        try {
            Assertions.assertNotNull(controllerAdvice.handleInvalidDataException(new InvalidDataException("Invalid inputs")));
        } catch (InvalidDataException e) {
            Assertions.assertNotNull(e);
        }
    }

    @Test
    void testApiExceptionHandlerTestsHandleInternalErrorException() {
        var controllerAdvice = new ApiExceptionHandler();
        try {
            Assertions.assertNotNull(controllerAdvice.handleInternalErrorException(new InternalServerErrorException("Internal server error", new Throwable())));
        } catch (InvalidDataException e) {
            Assertions.assertNotNull(e);
        }
    }

    @Test
    void testApiExceptionHandlerTestsHandleMappingNotFoundException() {
        var controllerAdvice = new ApiExceptionHandler();
        try {
            Assertions.assertNotNull(controllerAdvice.handleMappingNotFoundException(new NoHandlerFoundException("GET", "/test", new HttpHeaders())));
        } catch (InvalidDataException e) {
            Assertions.assertNotNull(e);
        }
    }

    @Test
    void testApiExceptionHandlerTestsDataNotFoundException() {
        var controllerAdvice = new ApiExceptionHandler();
        try {
            Assertions.assertNotNull(controllerAdvice.dataNotFoundException(new NotFoundException("Data not found")));
        } catch (InvalidDataException e) {
            Assertions.assertNotNull(e);
        }
    }

}
