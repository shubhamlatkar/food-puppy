package com.foodgrid.notification.command.repository;

import com.foodgrid.notification.command.model.aggregate.UserNotification;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface UserNotificationRepository extends ReactiveMongoRepository<UserNotification, String> {
    @Tailable
    Flux<UserNotification> findByHostId(String hostId);
}