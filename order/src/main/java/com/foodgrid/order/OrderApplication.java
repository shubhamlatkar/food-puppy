package com.foodgrid.order;


import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.foodgrid")
@EnableMongoRepositories("com.foodgrid")
@EntityScan("com.foodgrid")
@RestController
@CrossOrigin("*")
@RequestMapping("/${endpoint.service}/${endpoint.version}")
@Slf4j
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Autowired
    private UserDetailsServiceImplementation userDetailsService;

    @Bean("initData")
    CommandLineRunner initData(MongoTemplate mongoTemplate) {
        return user -> {
            try {
                userDetailsService.initDatabase(mongoTemplate);
            } catch (Exception e) {
                log.error("Mongo DB not available");
            }
        };
    }

    @GetMapping("/")
    public ResponseEntity<String> getDefault() {
        return new ResponseEntity<>("Order Service ", HttpStatus.OK);
    }
}

