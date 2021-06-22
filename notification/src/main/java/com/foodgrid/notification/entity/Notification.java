package com.foodgrid.notification.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Notification {
    @Id
    private String id;
    private String name;
    private String hostId;

    public Notification() {
    }

    public Notification(String name, String hostId) {
        this.name = name;
        this.hostId = hostId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hostId='" + hostId + '\'' +
                '}';
    }
}
