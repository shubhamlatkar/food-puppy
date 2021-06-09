package com.foodPuppy.restaurant_service.controller;

import com.foodPuppy.restaurant_service.documents.Restaurant;
import com.foodPuppy.restaurant_service.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("/")
    public ResponseEntity<?> getRestaurant() {
        restaurantRepository.save(new Restaurant("rosewood"));
        return new ResponseEntity<List<Restaurant>>(restaurantRepository.findAll(), HttpStatus.OK);
    }
}
