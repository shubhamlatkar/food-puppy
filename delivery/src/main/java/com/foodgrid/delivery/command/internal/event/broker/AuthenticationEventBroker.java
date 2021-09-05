package com.foodgrid.delivery.command.internal.event.broker;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.payload.dco.UserToUserAuthEvent;
import com.foodgrid.common.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.security.component.DeletedUsers;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.utility.UserTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Topic;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Component
@Slf4j
public class AuthenticationEventBroker {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("authenticationTopic")
    private Topic authenticationTopic;

    @Autowired
    private DeletedUsers deletedUsers;

    @Scheduled(fixedDelay = 1000)
    public void send() {
        List<User> users = userRepository.findAll();
        var start = new Date();
        start.setTime(new Date().getTime() - 10000);

        List<UserAuthEventDTO> userList = new ArrayList<>();
        users.forEach(user -> {
            if (user.getMetadata().getLastUpdatedAt().getTime() > start.getTime() && user.getType().equals(UserTypes.DELIVERY))
                userList.add(new UserToUserAuthEvent(user, UserTypes.DELIVERY).getUser());
        });

        if (deletedUsers.getUsers() != null && Boolean.FALSE.equals(deletedUsers.getUsers().isEmpty())) {
            deletedUsers.getUsers().forEach(user1 -> userList.add(new UserToUserAuthEvent(user1, UserTypes.DELIVERY).getUser()));
            deletedUsers.setUsers(new HashSet<>());
        }

        if (!userList.isEmpty()) {
            log.info("New activity: {}", userList);
            this.jmsMessagingTemplate.convertAndSend(authenticationTopic, new AuthenticationEvent(true, userList));
        }
    }
}