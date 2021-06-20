package com.foodgrid.common.utils;

public class UserEntity {
    private String username;

    public UserEntity() {
    }

    public UserEntity(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                '}';
    }
}
