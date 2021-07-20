package com.foodgrid.common.exception.model;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class InternalErrorExceptionDTO extends BaseExceptionDTO {
    private String cause;

    public InternalErrorExceptionDTO(String message, String cause) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, new Date());
        this.cause = cause;
    }
}
