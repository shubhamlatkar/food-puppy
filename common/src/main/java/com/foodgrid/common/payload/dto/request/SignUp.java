package com.foodgrid.common.payload.dto.request;

import com.foodgrid.common.security.utility.UserTypes;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
public class SignUp {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(max = 10)
    private String phone;

    private UserTypes type;

    public SignUp(String username, String email, Set<String> roles, String password, String phone, UserTypes type) {
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.phone = phone;
        this.type = type;
    }
}
