package com.foodgrid.delivery;

import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.utility.UserTypes;
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
public class DeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }

    @Autowired
    private UserDetailsServiceImplementation userDetailsService;

    @Bean
    CommandLineRunner initData(MongoTemplate mongoTemplate) {
        return user -> {
            try {
                userDetailsService.initDatabase(mongoTemplate);
                Set<String> roles = new HashSet<>();
                roles.add("ROLE_DELIVERY");
                userDetailsService.saveUser(new SignUp("testDelivery", "testDelivery@test.com", roles, "test", "12345678903", UserTypes.DELIVERY));
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
