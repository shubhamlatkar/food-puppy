package com.foodgrid.common.security.model.entity;

import com.foodgrid.common.utility.UserActivities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMetadata {
    private Date createdAt;
    private Date lastUpdatedAt;
    private UserActivities lastActivity;
    private String lastDeletedToken;

    public UserMetadata(Date createdAt, Date lastUpdatedAt, UserActivities lastActivity) {
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.lastActivity = lastActivity;
    }
}

