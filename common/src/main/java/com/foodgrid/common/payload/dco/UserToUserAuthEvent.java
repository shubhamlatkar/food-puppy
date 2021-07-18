package com.foodgrid.common.payload.dco;

import com.foodgrid.common.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.model.entity.TokenData;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserToUserAuthEvent {
    private UserAuthEventDTO user;

    public UserToUserAuthEvent(User user, UserTypes type) {
        this.user = new UserAuthEventDTO();
        this.user.setUserType(type);
        this.user.setUserId(user.getId());
        this.user.setActivity(user.getMetadata().getLastActivity());
        this.user.setUsername(user.getUsername());
        if (user.getMetadata().getLastActivity().equals(UserActivities.LOGIN))
            this.user.setToken(
                    user.getActiveTokens()
                            .stream()
                            .max(Comparator.comparing(TokenData::getCreatedAt))
                            .orElse(new TokenData()).getToken()
            );
        else if (user.getMetadata().getLastActivity().equals(UserActivities.LOGOUT))
            this.user.setToken(user.getMetadata().getLastDeletedToken());
        else
            this.user.setToken(null);
        this.user.setPassword(user.getPassword());
        this.user.setRole(user.getRoles().get(0).getName());
        this.user.setEmail(user.getEmail());
        this.user.setPhone(user.getPhone());
    }

}
