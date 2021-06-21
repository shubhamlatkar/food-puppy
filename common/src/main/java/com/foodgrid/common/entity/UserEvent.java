package com.foodgrid.common.entity;

import java.util.List;

public class UserEvent {
    private List<String> name;
    private String type;

    public UserEvent() {
    }

    public UserEvent(List<String> name, String type) {
        this.name = name;
        this.type = type;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "name=" + name +
                ", type='" + type + '\'' +
                '}';
    }
}
