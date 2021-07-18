package com.foodgrid.common.payload.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogIn {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
