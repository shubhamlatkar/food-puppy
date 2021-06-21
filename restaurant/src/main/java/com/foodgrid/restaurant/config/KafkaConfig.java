package com.foodgrid.restaurant.config;

import com.foodgrid.common.entity.User;
import com.foodgrid.common.entity.UserEvent;
import com.foodgrid.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class KafkaConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public Consumer<UserEvent> authentication() {
        return userEvent -> userRepository.saveAll(
                userEvent
                        .getName()
                        .stream()
                        .filter(username -> !username.isEmpty())
                        .map(User::new).collect(Collectors.toList()
                ));
    }
}
