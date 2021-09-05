package com.foodgrid.common.unit;

import com.foodgrid.common.exception.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Date;

@SpringBootTest(classes = {ApiExceptionDTO.class, BaseExceptionDTO.class, InternalErrorExceptionDTO.class, MappingNotFoundExceptionDTO.class, NotFoundDTO.class})
@AutoConfigureWebTestClient
class ExceptionDTOTests {

    @Test
    void testApiExceptionDTOTests() {
        var exceptionDto = new ApiExceptionDTO("Test exception msg", HttpStatus.BAD_GATEWAY, "Monogo unavailable");
        Assertions.assertNotNull(exceptionDto.toString());
        Assertions.assertNotNull(exceptionDto.getCause());
        Assertions.assertNotNull(exceptionDto.getMessage());
        Assertions.assertNotNull(exceptionDto.getStatus());
        Assertions.assertNotNull(exceptionDto.getTimestamp());
        Assertions.assertNotNull(exceptionDto.getClass());
    }

    @Test
    void testBaseExceptionDTOTests() {
        var exceptionDto = new BaseExceptionDTO("Test exception msg", HttpStatus.BAD_GATEWAY, new Date());
        Assertions.assertNotNull(exceptionDto.toString());
        Assertions.assertNotNull(exceptionDto.getMessage());
        Assertions.assertNotNull(exceptionDto.getStatus());
        Assertions.assertNotNull(exceptionDto.getTimestamp());
        Assertions.assertNotNull(exceptionDto.getClass());
    }

    @Test
    void testInternalErrorExceptionDTOTests() {
        var exceptionDto = new InternalErrorExceptionDTO("Test exception msg", "MongoDB unavailable");
        Assertions.assertNotNull(exceptionDto.toString());
        Assertions.assertNotNull(exceptionDto.getMessage());
        Assertions.assertNotNull(exceptionDto.getStatus());
        Assertions.assertNotNull(exceptionDto.getTimestamp());
        Assertions.assertNotNull(exceptionDto.getClass());
    }

    @Test
    void testMappingNotFoundExceptionDTOTests() {
        var exceptionDto = new MappingNotFoundExceptionDTO("Test exception msg", "MongoDB unavailable");
        Assertions.assertNotNull(exceptionDto.toString());
        Assertions.assertNotNull(exceptionDto.getMessage());
        Assertions.assertNotNull(exceptionDto.getStatus());
        Assertions.assertNotNull(exceptionDto.getTimestamp());
        Assertions.assertNotNull(exceptionDto.getClass());
    }

    @Test
    void testNotFoundDTOTests() {
        var exceptionDto = new NotFoundDTO("Test exception msg", "MongoDB unavailable");
        Assertions.assertNotNull(exceptionDto.toString());
        Assertions.assertNotNull(exceptionDto.getMessage());
        Assertions.assertNotNull(exceptionDto.getStatus());
        Assertions.assertNotNull(exceptionDto.getTimestamp());
        Assertions.assertNotNull(exceptionDto.getClass());
    }
}
