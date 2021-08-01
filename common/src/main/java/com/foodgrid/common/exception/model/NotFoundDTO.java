package com.foodgrid.common.exception.model;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class NotFoundDTO extends BaseExceptionDTO {
    private String mapping;

    public NotFoundDTO(String message, String mapping) {
        super(message, HttpStatus.NOT_FOUND, new Date());
        this.mapping = mapping;
    }
}
