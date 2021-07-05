package com.foodgrid.common.security.payload.dco;

import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.model.entity.TokenData;
import com.foodgrid.common.security.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.security.utility.UserActivities;
import com.foodgrid.common.security.utility.UserTypes;

import java.util.Comparator;

public class UserToUserAuthEvent {
    private UserAuthEventDTO user;

    public UserToUserAuthEvent() {
    }

    public UserToUserAuthEvent(User user, UserTypes type) {
        this.user = new UserAuthEventDTO();
        this.user.setUserType(type);
        this.user.setUserId(user.getId());
        this.user.setActivity(user.getMetadata().getLastActivity());
        if (user.getMetadata().getLastActivity().equals(UserActivities.LOGIN))
            this.user.setToken(
                    user.getActiveTokens()
                            .stream()
                            .max(Comparator.comparing(TokenData::getCreatedAt))
                            .orElse(new TokenData()).getToken()
            );
        else
            this.user.setToken(null);
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
