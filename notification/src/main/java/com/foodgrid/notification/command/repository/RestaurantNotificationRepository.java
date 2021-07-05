package com.foodgrid.notification.command.repository;

import com.foodgrid.notification.command.model.aggregate.RestaurantNotification;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface RestaurantNotificationRepository extends ReactiveMongoRepository<RestaurantNotification, String> {
    @Tailable
    Flux<RestaurantNotification> findByHostId(String hostId);
}