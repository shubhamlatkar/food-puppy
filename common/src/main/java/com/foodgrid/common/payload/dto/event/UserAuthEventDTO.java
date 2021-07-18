package com.foodgrid.common.payload.dto.event;

import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthEventDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private UserTypes userType;
    private String userId;
    private String username;
    private String password;
    private UserActivities activity;
    private String token;
    private String role;
    private String phone;
    private String email;
}
