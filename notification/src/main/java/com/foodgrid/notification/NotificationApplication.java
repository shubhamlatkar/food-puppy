package com.foodgrid.notification;

import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.notification.command.model.aggregate.DeliveryNotification;
import com.foodgrid.notification.command.model.aggregate.MetaData;
import com.foodgrid.notification.command.model.aggregate.RestaurantNotification;
import com.foodgrid.notification.command.model.aggregate.UserNotification;
import com.foodgrid.notification.command.repository.DeliveryNotificationRepository;
import com.foodgrid.notification.command.repository.RestaurantNotificationRepository;
import com.foodgrid.notification.command.repository.UserNotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan("com.foodgrid")
@EnableMongoRepositories("com.foodgrid")
@EntityScan("com.foodgrid")
@EnableEurekaClient
@Slf4j
public class NotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Autowired
    private RestaurantNotificationRepository restaurantNotificationRepository;

    @Autowired
    private DeliveryNotificationRepository deliveryNotificationRepository;

    @Autowired
    private UserDetailsServiceImplementation userDetailsService;

    @Bean("initData")
    CommandLineRunner initData(ReactiveMongoTemplate reactiveMongoTemplate, MongoTemplate mongoTemplate) {
        return notification -> {
            try {
                if (!mongoTemplate.collectionExists(MetaData.class))
                    mongoTemplate.dropCollection(MetaData.class);

                var userCollectionExists = reactiveMongoTemplate.collectionExists(UserNotification.class).block();
                var restaurantCollectionExists = reactiveMongoTemplate.collectionExists(RestaurantNotification.class).block();
                var deliveryCollectionExists = reactiveMongoTemplate.collectionExists(DeliveryNotification.class).block();
                if ((userCollectionExists == null || !userCollectionExists)
                        && (restaurantCollectionExists == null || !restaurantCollectionExists)
                        && (deliveryCollectionExists == null || !deliveryCollectionExists))
                    reactiveMongoTemplate.dropCollection(UserNotification.class)
                            .then(reactiveMongoTemplate.createCollection(UserNotification.class, CollectionOptions.empty().capped().size(10485)))
                            .then(reactiveMongoTemplate.dropCollection(RestaurantNotification.class))
                            .then(reactiveMongoTemplate.createCollection(RestaurantNotification.class, CollectionOptions.empty().capped().size(10485)))
                            .then(reactiveMongoTemplate.dropCollection(DeliveryNotification.class))
                            .then(reactiveMongoTemplate.createCollection(DeliveryNotification.class, CollectionOptions.empty().capped().size(10485)))
                            .block();
                var id = "60e5dc6a58f5eb36303eb999";
                userNotificationRepository.save(new UserNotification(UserActivities.SIGNUP.name(), id)).block();
                restaurantNotificationRepository.save(new RestaurantNotification(UserActivities.SIGNUP.name(), id)).block();
                deliveryNotificationRepository.save(new DeliveryNotification(UserActivities.SIGNUP.name(), id)).block();

                userDetailsService.initDatabase(mongoTemplate);
            } catch (Exception e) {
                log.info("Mongo db not available");
            }
        };
    }
}

