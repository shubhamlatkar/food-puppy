package com.foodgrid.restaurant;

import com.foodgrid.common.entity.User;
import com.foodgrid.common.repository.UserRepository;
import com.foodgrid.common.security.SecurityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@EnableEurekaClient
@CrossOrigin("*")
@ComponentScan("com.foodgrid")
@EnableMongoRepositories("com.foodgrid")
@EntityScan("com.foodgrid")
public class RestaurantApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantApplication.class, args);
    }

    @Autowired
    private SecurityEntity securityEntity;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = {"/restaurant/", "/"})
    public ResponseEntity<List<User>> defaultGet() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
}
