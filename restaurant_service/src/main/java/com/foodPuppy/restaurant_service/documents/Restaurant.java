package com.foodPuppy.restaurant_service.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
