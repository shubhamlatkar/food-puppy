package com.foodgrid.user.config;

import com.foodgrid.common.entity.User;
import com.foodgrid.common.entity.UserEvent;
import com.foodgrid.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Component
public class KafkaConfig {

    @Autowired
    private UserRepository userRepository;

    private final AtomicInteger count = new AtomicInteger();

    @Bean
    public Supplier<Flux<UserEvent>> authentication() {
        return () -> Flux.fromStream(Stream.generate(() -> {
            try {
                Thread.sleep(1000);
                List<User> users = userRepository.findAll();
                if (count.get() < users.size()) {
                    count.set(users.size());
                    Date start = new Date();
                    start.setTime(new Date().getTime() - 10000);
                    List<String> userList = new ArrayList<>();
                    users.forEach(user -> {
                        if (user.getCreatedAt().getTime() > start.getTime())
                            userList.add(user.getName());
                    });
                    return new UserEvent(userList, "login");
                } else {
                    return new UserEvent(new ArrayList<>(), "no new update");
                }
            } catch (Exception e) {
                return new UserEvent(new ArrayList<>(), "exception");
            }
        })).subscribeOn(Schedulers.boundedElastic()).share();
    }
}
