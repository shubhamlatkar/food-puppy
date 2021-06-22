package com.foodgrid.notification.controller;


import com.foodgrid.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@CrossOrigin("*")
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping(value = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> getMovies() {
        return notificationRepository.findAll()
                .map(notification -> ServerSentEvent.<String>builder()
                        .id(notification.getId())
                        .event("notification")
                        .data(notification.toString())
                        .build())
                .subscribeOn(Schedulers.boundedElastic());
    }
}
