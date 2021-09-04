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

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

@SpringBootApplication
@EnableConfigServer
@RestController
public class ConfigurationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigurationApplication.class, args);
    }

    @Autowired
    private SecretKey secretKey;

    private final Random random = new Random();

    @Bean
    CommandLineRunner initData() {
        return secret -> {
            int leftLimit = 48; // numeral '0'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 500;

            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            String secretString = Base64.getEncoder().encodeToString(generatedString.getBytes(StandardCharsets.UTF_8));
            secretKey.setSecret(secretString);
        };
    }

    @GetMapping("/api/v1/secret")
    public ResponseEntity<String> getSecret() {
        return new ResponseEntity<>(secretKey.getSecret(), HttpStatus.OK);
    }

}
