package com.foodgrid.restaurant.config;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.security.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.security.utility.UserActivities;
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
    public Consumer<AuthenticationEvent> authentication() {
        return authenticationEvent -> userRepository.saveAll(
                authenticationEvent
                        .getUsers()
                        .stream()
                        .filter(userAuthEventDTO -> userAuthEventDTO.getActivity() == UserActivities.LOGIN)
                        .map(UserAuthEventDTO::getUser)
                        .collect(Collectors.toList())
        );
    }
}
