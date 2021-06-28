package com.foodgrid.notification;

import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.payload.dto.request.SignUp;
import com.foodgrid.common.security.utility.UserActivities;
import com.foodgrid.notification.entity.Notification;
import com.foodgrid.notification.repository.NotificationRepository;
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

import java.util.HashSet;
import java.util.Set;


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
    private NotificationRepository notificationRepository;

    @Autowired
    private UserDetailsServiceImplementation userDetailsService;


    @Bean
    CommandLineRunner initData(ReactiveMongoTemplate reactiveMongoTemplate, MongoTemplate mongoTemplate) {
        return notification -> {
            reactiveMongoTemplate.dropCollection(Notification.class).then(reactiveMongoTemplate.createCollection(
                    Notification.class, CollectionOptions.empty().capped().size(10485))).block();
            notificationRepository.save(new Notification(UserActivities.SIGNUP, "60d8ae912e61a9526c025be3")).block();

            userDetailsService.initDatabase(mongoTemplate);

            Set<String> roles = new HashSet<>();
            roles.add("ROLE_USER");

            userDetailsService.saveUser(new SignUp("test", "test@test.com", roles, "test", "1234567890"));


        };
    }
}

