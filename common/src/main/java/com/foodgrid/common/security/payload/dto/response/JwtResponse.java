package com.foodgrid.common.security.payload.dto.response;

public class JwtResponse {

    private String token;
    private String username;
    private String id;

    public JwtResponse() {
    }

    public JwtResponse(String token, String username, String id) {
        this.token = token;
        this.username = username;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

