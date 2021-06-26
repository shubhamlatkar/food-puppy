package com.foodgrid.common.security.payload.dco;

import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.security.utility.UserTypes;

public class UserToUserAuthEvent {
    private UserAuthEventDTO user;

    public UserToUserAuthEvent() {
    }

    public UserToUserAuthEvent(User user, UserTypes type) {
        this.user = new UserAuthEventDTO();
        this.user.setUserType(type);
        this.user.setUserId(user.getId());
        this.user.setActivity(user.getMetadata().getLastActivity());
        this.user.setToken(user.getActiveTokens());
        this.user.setUser(user);
    }

    public UserAuthEventDTO getUser() {
        return user;
    }

    public void setUser(UserAuthEventDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserToUserAuthEvent{" +
                "user=" + user +
                '}';
    }
}
