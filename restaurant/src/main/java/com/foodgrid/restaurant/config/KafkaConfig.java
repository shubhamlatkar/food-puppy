package com.foodgrid.restaurant.config;

import com.foodgrid.common.entity.User;
import com.foodgrid.common.entity.UserEvent;
import com.foodgrid.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class KafkaConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public Consumer<UserEvent> authentication() {
        return userEvent -> userRepository.save(new User(userEvent.getName()));
    }
}
