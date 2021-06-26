package com.foodgrid.notification.config;

import com.foodgrid.common.event.AuthenticationEvent;
import com.foodgrid.common.security.utility.UserActivities;
import com.foodgrid.notification.entity.Notification;
import com.foodgrid.notification.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class KafkaConfig {

    @Autowired
    private NotificationRepository notificationRepository;

    private final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

    @Bean
    public Consumer<AuthenticationEvent> authentication() {
        return authenticationEvent -> notificationRepository.saveAll(
                authenticationEvent
                        .getUsers()
                        .stream()
                        .filter(userAuthEventDTO -> userAuthEventDTO.getActivity() == UserActivities.LOGIN)
                        .map(userAuthEventDTO ->
                                new Notification(userAuthEventDTO.getUser().getMetadata().getLastActivity(), userAuthEventDTO.getUserId())
                        ).collect(Collectors.toList())
        ).subscribe(result -> logger.info("Entity has been saved: {}", result));
    }
}
