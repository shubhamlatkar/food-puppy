package com.foodgrid.notification.query;

import com.foodgrid.notification.command.model.aggregate.DeliveryNotification;
import com.foodgrid.notification.command.model.aggregate.RestaurantNotification;
import com.foodgrid.notification.command.model.aggregate.UserNotification;
import com.foodgrid.notification.command.repository.DeliveryNotificationRepository;
import com.foodgrid.notification.command.repository.RestaurantNotificationRepository;
import com.foodgrid.notification.command.repository.UserNotificationRepository;
import com.foodgrid.notification.query.rest.NotificationResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

@SpringBootTest
@AutoConfigureWebTestClient
class NotificationResourceTests {
    @MockBean
    private UserNotificationRepository userNotificationRepository;

    @MockBean
    private RestaurantNotificationRepository restaurantNotificationRepository;

    @MockBean
    private DeliveryNotificationRepository deliveryNotificationRepository;

    @Autowired
    private NotificationResource notificationResource;

    @Test
    @DisplayName("Tests user type of getUserNotifications method of NotificationResource")
    void getUserNotificationsUserType() {
        doAnswer(invocationOnMock -> Flux.just(new UserNotification("1", "test", "1"))).when(userNotificationRepository).findByHostId(anyString());
        Assertions.assertNotNull(notificationResource.getUserNotifications("user", "1"));
    }

    @Test
    @DisplayName("Tests restaurant type of getUserNotifications method of NotificationResource")
    void getUserNotificationsRestaurantType() {
        doAnswer(invocationOnMock -> Flux.just(new RestaurantNotification("1", "test", "1"))).when(restaurantNotificationRepository).findByHostId(anyString());
        Assertions.assertNotNull(notificationResource.getUserNotifications("restaurant", "1"));
    }

    @Test
    @DisplayName("Tests delivery type of getUserNotifications method of NotificationResource")
    void getUserNotificationsDeliveryType() {
        doAnswer(invocationOnMock -> Flux.just(new DeliveryNotification("1", "test", "1"))).when(deliveryNotificationRepository).findByHostId(anyString());
        Assertions.assertNotNull(notificationResource.getUserNotifications("delivery", "1"));
    }

    @Test
    @DisplayName("Tests default type of getUserNotifications method of NotificationResource")
    void getUserNotificationsDefault() {
        doAnswer(invocationOnMock -> Flux.just(new UserNotification("1", "test", "1"))).when(userNotificationRepository).findByHostId(anyString());
        Assertions.assertNotNull(notificationResource.getUserNotifications("test", "1"));
    }

}
