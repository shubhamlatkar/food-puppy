package com.foodgrid.common.exception.handler;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.common.exception.exceptions.InvalidDataException;
import com.foodgrid.common.exception.model.ApiExceptionDTO;
import com.foodgrid.common.exception.model.InternalErrorExceptionDTO;
import com.foodgrid.common.exception.model.MappingNotFoundExceptionDTO;
import com.foodgrid.common.payload.logger.ExceptionLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(value = {InvalidDataException.class})
    public ResponseEntity<ApiExceptionDTO> handleInvalidDataException(InvalidDataException e) {
        log.error(
                new ExceptionLog(
                        this.getClass().getName(),
                        "handleInvalidDataException",
                        e.getMessage(),
                        HttpStatus.BAD_REQUEST
                ).toString(),
                e
        );
        return new ResponseEntity<>(
                new ApiExceptionDTO(e.getMessage(), HttpStatus.BAD_REQUEST, "Invalid data")
                , HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {InternalServerErrorException.class})
    public ResponseEntity<InternalErrorExceptionDTO> handleInternalErrorException(InternalServerErrorException e) {
        log.error(
                new ExceptionLog(
                        this.getClass().getName(),
                        "handleInternalErrorException",
                        e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR
                ).toString(),
                e
        );
        return new ResponseEntity<>(
                new InternalErrorExceptionDTO(e.getMessage(), e.getCause().toString()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<MappingNotFoundExceptionDTO> handleMappingNotFoundException(NoHandlerFoundException e) {
        log.error(
                new ExceptionLog(
                        this.getClass().getName(),
                        "handleMappingNotFoundException",
                        e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR
                ).toString(),
                e
        );
        return new ResponseEntity<>(new MappingNotFoundExceptionDTO(e.getMessage(), "Not handler for the given mapping"), HttpStatus.NOT_FOUND);
    }
}
