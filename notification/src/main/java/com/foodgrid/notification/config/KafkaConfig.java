package com.foodgrid.notification.config;

import com.foodgrid.common.entity.UserEvent;
import com.foodgrid.notification.entity.Notification;
import com.foodgrid.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class KafkaConfig {

    @Autowired
    private NotificationRepository notificationRepository;

    @Bean
    public Consumer<UserEvent> authentication() {
        return userEvent -> notificationRepository.saveAll(
                userEvent
                        .getName()
                        .stream()
                        .filter(username -> !username.isEmpty())
                        .map(username -> new Notification(username, "1")).collect(Collectors.toList()
                ));
    }
}
