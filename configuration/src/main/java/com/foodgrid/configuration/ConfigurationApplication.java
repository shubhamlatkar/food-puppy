package com.foodgrid.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigServer
@RestController
public class ConfigurationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigurationApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private SecretKey secretKey;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    CommandLineRunner initData() {
        return secret -> {
            ResponseEntity<String> response
                    = restTemplate.getForEntity("https://keygen.io/api.php?name=sha512", String.class);
            secretKey.setSecret(response.getBody());
        };
    }

    @GetMapping("/api/v1/secret")
    public ResponseEntity<String> getSecret() {
        return new ResponseEntity<>(secretKey.getSecret(), HttpStatus.OK);
    }

}
