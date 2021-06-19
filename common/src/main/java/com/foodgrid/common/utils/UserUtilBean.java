package com.foodgrid.common.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUtilBean {
    @Bean
    public UserEntity getUser() {
        return new UserEntity("test");
    }
}
