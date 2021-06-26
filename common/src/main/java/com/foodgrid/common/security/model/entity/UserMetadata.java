package com.foodgrid.common.security.model.entity;

import com.foodgrid.common.security.utility.UserActivities;

import java.util.Date;

public class UserMetadata {
    private Date createdAt;
    private Date lastUpdatedAt;
    private UserActivities lastActivity;

    public UserMetadata() {
    }

    public UserMetadata(Date createdAt, Date lastUpdatedAt, UserActivities lastActivity) {
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.lastActivity = lastActivity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public UserActivities getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(UserActivities lastActivity) {
        this.lastActivity = lastActivity;
    }

    @Override
    public String toString() {
        return "UserMetadata{" +
                "createdAt=" + createdAt +
                ", lastUpdated=" + lastUpdatedAt +
                ", lastActivity=" + lastActivity +
                '}';
    }
}

