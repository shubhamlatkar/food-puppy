package com.foodgrid.common.security.payload.dto.request;

import javax.validation.constraints.NotBlank;

public class LogIn {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public LogIn() {
    }

    public LogIn(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LogIn{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
