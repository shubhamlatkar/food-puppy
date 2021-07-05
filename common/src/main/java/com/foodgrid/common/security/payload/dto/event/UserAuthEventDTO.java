package com.foodgrid.common.security.payload.dto.event;

import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.utility.UserActivities;
import com.foodgrid.common.security.utility.UserTypes;

public class UserAuthEventDTO {
    private UserTypes userType;
    private String userId;
    private String username;
    private UserActivities activity;
    private String token;
    private User user;

    public UserAuthEventDTO() {
    }

    public UserAuthEventDTO(UserTypes userType, String userId, String username, UserActivities activity, String token, User user) {
        this.userType = userType;
        this.userId = userId;
        this.username = username;
        this.activity = activity;
        this.token = token;
        this.user = user;
    }

    public UserTypes getUserType() {
        return userType;
    }

    public void setUserType(UserTypes userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserActivities getActivity() {
        return activity;
    }

    public void setActivity(UserActivities activity) {
        this.activity = activity;
    }

    public String getToken() {
        return token;
    }

    public void     setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserAuthEventDTO{" +
                "userType=" + userType +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", activity=" + activity +
                ", token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
