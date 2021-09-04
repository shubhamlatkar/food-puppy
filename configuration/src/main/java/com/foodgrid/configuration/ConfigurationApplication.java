package com.foodgrid.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
@EnableConfigServer
@RestController
public class ConfigurationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigurationApplication.class, args);
    }

    @Autowired
    private SecretKey secretKey;

    @Bean
    CommandLineRunner initData() {
        return secret -> {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[500];
            sr.nextBytes(salt);
            secretKey.setSecret(Base64.getEncoder().encodeToString(salt));
        };
    }

    @GetMapping("/api/v1/secret")
    public ResponseEntity<String> getSecret() {
        return new ResponseEntity<>(secretKey.getSecret(), HttpStatus.OK);
    }

}
