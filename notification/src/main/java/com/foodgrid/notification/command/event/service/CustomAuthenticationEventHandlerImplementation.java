package com.foodgrid.notification.command.event.service;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.skeleton.event.handler.AuthenticationEventHandler;
import com.foodgrid.notification.command.model.aggregate.DeliveryNotification;
import com.foodgrid.notification.command.model.aggregate.RestaurantNotification;
import com.foodgrid.notification.command.model.aggregate.UserNotification;
import com.foodgrid.notification.command.repository.DeliveryNotificationRepository;
import com.foodgrid.notification.command.repository.RestaurantNotificationRepository;
import com.foodgrid.notification.command.repository.UserNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationEventHandlerImplementation implements AuthenticationEventHandler {


    private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEventHandlerImplementation.class);

    @Autowired
    private RestaurantNotificationRepository restaurantNotificationRepository;

    @Autowired
    private DeliveryNotificationRepository deliveryNotificationRepository;

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Override
    public void authConsumer(AuthenticationEvent event) {
        if (Boolean.TRUE.equals(event.getUpdated())) {
            for (UserAuthEventDTO userAuthEventDTO : event.getUsers()) {
                switch (userAuthEventDTO.getActivity()) {
                    case LOGIN:
                        login(userAuthEventDTO.getToken(), userAuthEventDTO.getUser());
                        break;
                    case SIGNUP:
                        signup(userAuthEventDTO.getUser());
                        break;
                    case LOGOUT:
                        logout(userAuthEventDTO.getToken(), userAuthEventDTO.getUser());
                        break;
                    case LOGOUT_ALL:
                        logoutAll(userAuthEventDTO.getUser());
                        break;
                    case DELETE:
                        delete(userAuthEventDTO.getUser());
                        break;
                    case PATCH:
                    case CHANGE_PASSWORD:
                        patch(userAuthEventDTO.getUser());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void patch(User user) {
        saveData(user);
    }

    @Override
    public void delete(User user) {
        logger.info("In delete method");
    }

    @Override
    public void logout(String jwt, User user) {
        saveData(user);
    }

    @Override
    public void login(String jwt, User user) {
        saveData(user);
    }

    @Override
    public void logoutAll(User user) {
        saveData(user);
    }

    @Override
    public void signup(User user) {
        saveData(user);
    }

    private void saveData(User user) {
        switch (user.getType()) {
            case USER:
                userNotificationRepository.save(new UserNotification(user.getType().name(), user.getId())).subscribe(result -> logger.info("User Entity has been saved: {}", result));
                break;
            case RESTAURANT:
                restaurantNotificationRepository.save(new RestaurantNotification(user.getType().name(), user.getId())).subscribe(result -> logger.info("Restaurant Entity has been saved: {}", result));
                break;
            case DELIVERY:
                deliveryNotificationRepository.save(new DeliveryNotification(user.getType().name(), user.getId())).subscribe(result -> logger.info("Delivery Entity has been saved: {}", result));
                break;
            default:
                break;
        }
    }
}
