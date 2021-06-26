package com.foodgrid.notification.entity;

import com.foodgrid.common.security.utility.UserActivities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Notification {
    @Id
    private String id;
    private UserActivities name;
    private String hostId;

    public Notification() {
    }

    public Notification(UserActivities name, String hostId) {
        this.name = name;
        this.hostId = hostId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserActivities getName() {
        return name;
    }

    public void setName(UserActivities name) {
        this.name = name;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", name=" + name +
                ", hostId='" + hostId + '\'' +
                '}';
    }
}
