package com.foodPuppy.restaurant_service.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Document
public class Restaurant {

    @Id
    private String id;

    @NotNull
    @Size(max = 20)
    private String restaurant_name;

    public Restaurant(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public Restaurant() {
    }

    public Restaurant(String id, String restaurant_name) {
        this.id = id;
        this.restaurant_name = restaurant_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", restaurant_name='" + restaurant_name + '\'' +
                '}';
    }
}
