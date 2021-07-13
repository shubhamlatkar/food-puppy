package com.foodgrid.notification.query.rest;

import com.foodgrid.notification.command.repository.DeliveryNotificationRepository;
import com.foodgrid.notification.command.repository.RestaurantNotificationRepository;
import com.foodgrid.notification.command.repository.UserNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@CrossOrigin("*")
@RestController
@RequestMapping("/events")
public class NotificationResource {

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Autowired
    private RestaurantNotificationRepository restaurantNotificationRepository;

    @Autowired
    private DeliveryNotificationRepository deliveryNotificationRepository;

    private static final String TYPE = "notification";

    @GetMapping(value = "/user/{hostId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> getUserNotifications(@PathVariable String hostId) {
        return userNotificationRepository.findByHostId(hostId)
                .map(notification -> ServerSentEvent.<String>builder()
                        .id(notification.getId())
                        .event(TYPE)
                        .data(notification.getMessage())
                        .build())
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping(value = "/restaurant/{hostId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> getRestaurantNotifications(@PathVariable String hostId) {
        return restaurantNotificationRepository.findByHostId(hostId)
                .map(notification -> ServerSentEvent.<String>builder()
                        .id(notification.getId())
                        .event(TYPE)
                        .data(notification.getMessage())
                        .build())
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping(value = "/delivery/{hostId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> getDeliveryNotifications(@PathVariable String hostId) {
        return deliveryNotificationRepository.findByHostId(hostId)
                .map(notification -> ServerSentEvent.<String>builder()
                        .id(notification.getId())
                        .event(TYPE)
                        .data(notification.getMessage())
                        .build())
                .subscribeOn(Schedulers.boundedElastic());
    }
}
