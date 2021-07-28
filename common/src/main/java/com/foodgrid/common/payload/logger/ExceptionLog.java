package com.foodgrid.common.payload.logger;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionLog extends BaseLog {
    private String message;
    private HttpStatus status;

    public ExceptionLog(String classname, String methodName, String message, HttpStatus status) {
        super(classname, methodName, new Date());
        this.message = message;
        this.status = status;
    }

}
