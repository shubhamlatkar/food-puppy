package com.foodgrid.user.config;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.payload.dco.UserToUserAuthEvent;
import com.foodgrid.common.security.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.security.utility.UserTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Component
public class KafkaConfig {

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

    @Bean
    public Supplier<Flux<AuthenticationEvent>> authentication() {
        return () -> Flux.fromStream(Stream.generate(() -> {
            try {
                Thread.sleep(1000);
                List<User> users = userRepository.findAll();

                var start = new Date();
                start.setTime(new Date().getTime() - 10000);

                List<UserAuthEventDTO> userList = new ArrayList<>();
                users.forEach(user -> {
                    if (user.getMetadata().getLastUpdatedAt().getTime() > start.getTime())
                        userList.add(new UserToUserAuthEvent(user, UserTypes.USER).getUser());
                });

                if (!userList.isEmpty()) {
                    logger.info("New activity: {}", userList);
                    return new AuthenticationEvent(true, userList);
                } else {
                    logger.info("No new activity ");
                    return new AuthenticationEvent(false, new ArrayList<>());
                }
            } catch (InterruptedException e) {
                logger.error("InterruptedException: ", e);
                Thread.currentThread().interrupt();
                return new AuthenticationEvent(false, new ArrayList<>());
            } catch (Exception e) {
                return new AuthenticationEvent(false, new ArrayList<>());
            }
        })).subscribeOn(Schedulers.boundedElastic()).share();
    }
}
