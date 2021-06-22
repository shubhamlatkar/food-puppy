package com.foodgrid.notification.repository;

import com.foodgrid.notification.entity.Notification;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface NotificationRepository extends ReactiveMongoRepository<Notification, String> {
    @Tailable
    Flux<Notification> findByHostId(String hostId);
}
