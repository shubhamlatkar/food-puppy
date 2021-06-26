package com.foodgrid.common.security.payload.dto.event;

import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.utility.UserActivities;
import com.foodgrid.common.security.utility.UserTypes;

import java.util.List;

public class UserAuthEventDTO {
    private UserTypes userType;
    private String userId;
    private UserActivities activity;
    private List<String> token;
    private User user;

    public UserAuthEventDTO() {
    }

    public UserAuthEventDTO(UserTypes userType, String userId, UserActivities activity, List<String> token, User user) {
        this.userType = userType;
        this.userId = userId;
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

    public List<String> getToken() {
        return token;
    }

    public void setToken(List<String> token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserAuthEventDTO{" +
                "userType='" + userType + '\'' +
                ", userId='" + userId + '\'' +
                ", activity=" + activity +
                ", token=" + token +
                ", user=" + user +
                '}';
    }
}
