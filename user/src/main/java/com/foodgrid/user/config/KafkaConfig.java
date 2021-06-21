package com.foodgrid.user.config;

import com.foodgrid.common.entity.User;
import com.foodgrid.common.entity.UserEvent;
import com.foodgrid.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Component
public class KafkaConfig {
    @Autowired
    private UserRepository userRepository;

    @Bean
    public Supplier<Flux<UserEvent>> authentication() {
        return () -> Flux.fromStream(Stream.generate(() -> {
            try {
                Thread.sleep(1000);
                var count = 0;
                List<User> users = userRepository.findAll();
                if (count < users.size()) {
                    count = users.size();
                    return new UserEvent(users.get(users.size() - 1).getName(), "login");
                } else
                    return new UserEvent("ignore it", "no new update");
            } catch (Exception e) {
                return new UserEvent("token exception", "exception");
            }
        })).subscribeOn(Schedulers.boundedElastic()).share();
    }
}
