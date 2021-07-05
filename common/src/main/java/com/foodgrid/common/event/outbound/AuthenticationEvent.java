package com.foodgrid.common.event.outbound;

import com.foodgrid.common.security.payload.dto.event.UserAuthEventDTO;

import java.util.List;

public class AuthenticationEvent {
    private Boolean isUpdated;

    private List<UserAuthEventDTO> users;

    public AuthenticationEvent() {
    }

    public AuthenticationEvent(Boolean isUpdated, List<UserAuthEventDTO> users) {
        this.isUpdated = isUpdated;
        this.users = users;
    }

    public Boolean getUpdated() {
        return isUpdated;
    }

    public void setUpdated(Boolean updated) {
        isUpdated = updated;
    }

    public List<UserAuthEventDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserAuthEventDTO> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "AuthenticationEvent{" +
                "isUpdated=" + isUpdated +
                ", users=" + users +
                '}';
    }
}
