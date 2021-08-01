package com.foodgrid.restaurant.query.internal.repository;

import com.foodgrid.restaurant.query.internal.model.aggregate.MenuQueryModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MenuQueryRepository extends MongoRepository<MenuQueryModel, String> {
    Optional<MenuQueryModel> findByRestaurantId(String restaurantId);

    Boolean existsByRestaurantId(String restaurantId);
}
