package com.foodgrid.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityBean {
    @Bean
    public SecurityEntity getSecurityEntity() {
        return new SecurityEntity("test security");
    }
}
