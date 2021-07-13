package com.foodgrid.user;


import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.security.utility.UserTypes;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@RestController
@EnableEurekaClient
@CrossOrigin("*")
@ComponentScan("com.foodgrid")
@EnableMongoRepositories("com.foodgrid")
@EntityScan("com.foodgrid")
@EnableScheduling
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }


    @Autowired
    private UserDetailsServiceImplementation userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Bean
    CommandLineRunner initData(MongoTemplate mongoTemplate) {
        return user -> {
            userDetailsService.initDatabase(mongoTemplate);
            Set<String> roles = new HashSet<>();
            roles.add("ROLE_USER");
            userDetailsService.saveUser(new SignUp("test", "test@test.com", roles, "test", "1234567890", UserTypes.USER));
        };
    }

    @GetMapping(value = {"/user/", "/"})
    public ResponseEntity<List<User>> defaultGet() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

}
