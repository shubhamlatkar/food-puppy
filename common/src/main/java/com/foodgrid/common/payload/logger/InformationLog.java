package com.foodgrid.common.payload.logger;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationLog extends BaseLog {
    private String message;

    public InformationLog(String classname, String methodName, String message) {
        super(classname, methodName, new Date());
        this.message = message;
    }
}
