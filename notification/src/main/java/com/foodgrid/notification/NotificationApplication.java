package com.foodgrid.notification;

import com.foodgrid.notification.entity.Notification;
import com.foodgrid.notification.repository.NotificationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.CollectionOptions;
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

    @Bean
    CommandLineRunner initData(ReactiveMongoTemplate reactiveMongoTemplate, NotificationRepository notificationRepository) {
        return notification -> {
            reactiveMongoTemplate.dropCollection(Notification.class).then(reactiveMongoTemplate.createCollection(
                    Notification.class, CollectionOptions.empty().capped().size(10485))).block();
            notificationRepository.save(new Notification("Welcome")).block();
        };
    }
}

