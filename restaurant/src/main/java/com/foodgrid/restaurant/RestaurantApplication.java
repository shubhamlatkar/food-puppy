package com.foodgrid.restaurant;

import com.foodgrid.common.security.SecurityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaClient
@CrossOrigin("*")
@ComponentScan("com.foodgrid")
public class RestaurantApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantApplication.class, args);
    }

    @Autowired
    private SecurityEntity securityEntity;

    @GetMapping(value = {"/restaurant/", "/"})
    public ResponseEntity<String> defaultGet() {
        return new ResponseEntity<>("Restaurant Service " + securityEntity, HttpStatus.OK);
    }
}
