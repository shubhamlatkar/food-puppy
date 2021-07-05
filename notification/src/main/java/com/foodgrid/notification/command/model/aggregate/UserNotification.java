package com.foodgrid.notification.command.model.aggregate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserNotification {
    @Id
    private String id;
    private String message;
    private String hostId;

    public UserNotification(String message, String hostId) {
        this.message = message;
        this.hostId = hostId;
    }

    public UserNotification() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    @Override
    public String toString() {
        return "UserNotification{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", hostId='" + hostId + '\'' +
                '}';
    }
}
