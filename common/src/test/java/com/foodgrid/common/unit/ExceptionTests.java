package com.foodgrid.common.unit;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.common.exception.exceptions.InvalidDataException;
import com.foodgrid.common.exception.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {InternalServerErrorException.class, InvalidDataException.class, NotFoundException.class})
@AutoConfigureWebTestClient
class ExceptionTests {

    @Test
    void testInternalServerErrorExceptionTests() {
        var exception = new InternalServerErrorException("Test internal error", new Throwable());
        var exceptionMsg = new InternalServerErrorException("Test msg");
        Assertions.assertEquals("Test internal error", exception.getMessage());
        Assertions.assertEquals("Test msg", exceptionMsg.getMessage());
    }

    @Test
    void testInvalidDataExceptionTests() {
        var exception = new InvalidDataException("Test internal error", new Throwable());
        var exceptionMsg = new InvalidDataException("Test msg");
        Assertions.assertEquals("Test internal error", exception.getMessage());
        Assertions.assertEquals("Test msg", exceptionMsg.getMessage());
    }

    @Test
    void testNotFoundExceptionTests() {
        var exception = new NotFoundException("Test internal error", new Throwable());
        var plainException = new NotFoundException();
        var exceptionMsg = new NotFoundException("Test msg");
        Assertions.assertEquals("Test internal error", exception.getMessage());
        Assertions.assertEquals("Test msg", exceptionMsg.getMessage());
        Assertions.assertNotNull(plainException.toString());
    }

}
