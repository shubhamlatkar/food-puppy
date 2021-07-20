package com.foodgrid.common.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseExceptionDTO {
    private String message;
    private HttpStatus status;
    private Date timestamp;
}
