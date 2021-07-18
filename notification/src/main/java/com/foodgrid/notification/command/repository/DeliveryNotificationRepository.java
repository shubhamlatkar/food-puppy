package com.foodgrid.notification.command.repository;

import com.foodgrid.notification.command.model.aggregate.DeliveryNotification;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface DeliveryNotificationRepository extends ReactiveMongoRepository<DeliveryNotification, String> {
    @Tailable
    Flux<DeliveryNotification> findByHostId(String hostId);
}
