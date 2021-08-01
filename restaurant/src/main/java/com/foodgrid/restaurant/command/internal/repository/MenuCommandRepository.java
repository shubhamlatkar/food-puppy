package com.foodgrid.restaurant.command.internal.repository;

import com.foodgrid.restaurant.command.internal.model.aggregate.MenuCommandModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MenuCommandRepository extends MongoRepository<MenuCommandModel, String> {
    Optional<MenuCommandModel> findByRestaurantId(String restaurantId);

    Boolean existsByRestaurantId(String restaurantId);
}
