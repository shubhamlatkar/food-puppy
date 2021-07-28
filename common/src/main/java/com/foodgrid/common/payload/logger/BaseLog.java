package com.foodgrid.common.payload.logger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseLog {
    private String classname;
    private String methodName;
    private Date timestamp;
}
