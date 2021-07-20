package com.foodgrid.common.exception.model;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ApiExceptionDTO extends BaseExceptionDTO {
    private String cause;

    public ApiExceptionDTO(String message, HttpStatus status, String cause) {
        super(message, status, new Date());
        this.cause = cause;
    }
}
