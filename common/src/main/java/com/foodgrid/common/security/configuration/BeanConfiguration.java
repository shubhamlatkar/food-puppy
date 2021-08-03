package com.foodgrid.common.security.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    @LoadBalanced
    @Qualifier("internal")
    public RestTemplate internalRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Qualifier("external")
    public RestTemplate externalRestTemplate() {
        return new RestTemplate();
    }

}
