package com.foodgrid.common.payload.dto.response;

import com.foodgrid.common.utility.UserActivities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationActionResponse {
    private UserActivities activity;
    private Boolean isSuccessful;
    private Date timestamp;
    private String message;
}
