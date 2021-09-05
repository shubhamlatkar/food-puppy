package com.foodgrid.restaurant;

import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.utility.UserTypes;
import com.foodgrid.restaurant.command.internal.model.aggregate.MenuCommandModel;
import com.foodgrid.restaurant.query.internal.model.aggregate.MenuQueryModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.foodgrid")
@EnableMongoRepositories("com.foodgrid")
@EntityScan("com.foodgrid")
@EnableScheduling
@Controller
@Slf4j
public class RestaurantApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantApplication.class, args);
    }

    @Autowired
    private UserDetailsServiceImplementation userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Bean
    CommandLineRunner initData(MongoTemplate mongoTemplate) {
        return restaurant -> {
            try {
                mongoTemplate.dropCollection(MenuCommandModel.class);
                mongoTemplate.dropCollection(MenuQueryModel.class);
                userDetailsService.initDatabase(mongoTemplate);
                Set<String> roles = new HashSet<>();
                roles.add("ROLE_RESTAURANT");
                userDetailsService.saveUser(new SignUp("testRestaurant", "testRestaurant@test.com", roles, "test", "12345678901", UserTypes.RESTAURANT));
            } catch (Exception e) {
                log.error("Mongo DB not available");
            }
        };
    }

    @GetMapping(value = "/member/**")
    public String redirect() {
        return "forward:/";
    }
}
