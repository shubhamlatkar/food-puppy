package com.foodgrid.user.configuration;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.payload.dco.UserToUserAuthEvent;
import com.foodgrid.common.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.security.utility.UserTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class AuthenticationScheduler {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedDelay = 1000)
    public void send() {
        List<User> users = userRepository.findAll();

        var start = new Date();
        start.setTime(new Date().getTime() - 10000);

        List<UserAuthEventDTO> userList = new ArrayList<>();
        users.forEach(user -> {
            if (user.getMetadata().getLastUpdatedAt().getTime() > start.getTime())
                userList.add(new UserToUserAuthEvent(user, UserTypes.USER).getUser());
        });

        if (!userList.isEmpty()) {
            log.info("New activity: {}", userList);
            this.jmsMessagingTemplate.convertAndSend("authentication", new AuthenticationEvent(true, userList));
        }
    }
}
