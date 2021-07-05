package com.foodgrid.notification;

import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.utility.UserActivities;
import com.foodgrid.notification.command.model.aggregate.DeliveryNotification;
import com.foodgrid.notification.command.model.aggregate.RestaurantNotification;
import com.foodgrid.notification.command.model.aggregate.UserNotification;
import com.foodgrid.notification.command.repository.DeliveryNotificationRepository;
import com.foodgrid.notification.command.repository.RestaurantNotificationRepository;
import com.foodgrid.notification.command.repository.UserNotificationRepository;
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

    @Bean
    CommandLineRunner initData(ReactiveMongoTemplate reactiveMongoTemplate, MongoTemplate mongoTemplate) {
        return notification -> {
            
            reactiveMongoTemplate.dropCollection(UserNotification.class)
                    .then(reactiveMongoTemplate.createCollection(UserNotification.class, CollectionOptions.empty().capped().size(10485)))
                    .then(reactiveMongoTemplate.dropCollection(RestaurantNotification.class))
                    .then(reactiveMongoTemplate.createCollection(RestaurantNotification.class, CollectionOptions.empty().capped().size(10485)))
                    .then(reactiveMongoTemplate.dropCollection(DeliveryNotification.class))
                    .then(reactiveMongoTemplate.createCollection(DeliveryNotification.class, CollectionOptions.empty().capped().size(10485)))
                    .block();

            userNotificationRepository.save(new UserNotification(UserActivities.SIGNUP.name(), "1")).block();
            restaurantNotificationRepository.save(new RestaurantNotification(UserActivities.SIGNUP.name(), "1")).block();
            deliveryNotificationRepository.save(new DeliveryNotification(UserActivities.SIGNUP.name(), "1")).block();

            userDetailsService.initDatabase(mongoTemplate);
        };
    }
}

