package com.foodPuppy.restaurant_service.repository;

import com.foodPuppy.restaurant_service.documents.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
}
