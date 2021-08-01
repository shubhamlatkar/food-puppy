package com.foodgrid.common.exception.handler;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.common.exception.exceptions.InvalidDataException;
import com.foodgrid.common.exception.exceptions.NotFoundException;
import com.foodgrid.common.exception.model.ApiExceptionDTO;
import com.foodgrid.common.exception.model.InternalErrorExceptionDTO;
import com.foodgrid.common.exception.model.MappingNotFoundExceptionDTO;
import com.foodgrid.common.exception.model.NotFoundDTO;
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
        log.error("Error caught InvalidDataException", e);
        return new ResponseEntity<>(
                new ApiExceptionDTO(e.getMessage(), HttpStatus.BAD_REQUEST, "Invalid data")
                , HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {InternalServerErrorException.class})
    public ResponseEntity<InternalErrorExceptionDTO> handleInternalErrorException(InternalServerErrorException e) {
        log.error("Error caught InternalServerErrorException", e);
        return new ResponseEntity<>(
                new InternalErrorExceptionDTO(e.getMessage(), e.getCause().toString()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<MappingNotFoundExceptionDTO> handleMappingNotFoundException(NoHandlerFoundException e) {
        log.error("Error caught NoHandlerFoundException", e);
        return new ResponseEntity<>(new MappingNotFoundExceptionDTO(e.getMessage(), "Not handler for the given mapping"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<NotFoundDTO> dataNotFoundException(NotFoundException e) {
        log.error("Error caught NotFoundException", e);
        return new ResponseEntity<>(new NotFoundDTO(e.getMessage(), "No Data found..."), HttpStatus.NOT_FOUND);
    }
}
