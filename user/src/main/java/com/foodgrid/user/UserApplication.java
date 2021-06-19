package com.foodgrid.user;

import com.foodgrid.common.utils.UserEntity;
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
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }


    @Autowired
    private UserEntity userEntity;

    @GetMapping(value = {"/user/", "/"})
    public ResponseEntity<String> defaultGet() {
        return new ResponseEntity<>("User Service " + userEntity, HttpStatus.OK);
    }

}
