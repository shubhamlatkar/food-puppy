package com.foodgrid.notification.command.event.service;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.event.service.AuthenticationEventHandler;
import com.foodgrid.common.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.notification.command.model.aggregate.DeliveryNotification;
import com.foodgrid.notification.command.model.aggregate.MetaData;
import com.foodgrid.notification.command.model.aggregate.RestaurantNotification;
import com.foodgrid.notification.command.model.aggregate.UserNotification;
import com.foodgrid.notification.command.repository.DeliveryNotificationRepository;
import com.foodgrid.notification.command.repository.MetaDataRepository;
import com.foodgrid.notification.command.repository.RestaurantNotificationRepository;
import com.foodgrid.notification.command.repository.UserNotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomAuthenticationEventHandlerImplementation implements AuthenticationEventHandler {

    @Autowired
    private RestaurantNotificationRepository restaurantNotificationRepository;

    @Autowired
    private DeliveryNotificationRepository deliveryNotificationRepository;

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Autowired
    private MetaDataRepository metaDataRepository;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;


    @Override
    @JmsListener(destination = "${event.authentication}")
    public void authConsumer(AuthenticationEvent event) {
        if (Boolean.TRUE.equals(event.getIsUpdated()) && event.getUsers() != null) {
            for (UserAuthEventDTO userAuthEventDTO : event.getUsers()) {
                switch (userAuthEventDTO.getActivity()) {
                    case LOGIN:
                        login(userAuthEventDTO);
                        break;
                    case SIGNUP:
                        signup(userAuthEventDTO);
                        break;
                    case LOGOUT:
                        logout(userAuthEventDTO);
                        break;
                    case LOGOUT_ALL:
                        logoutAll(userAuthEventDTO);
                        break;
                    case DELETE:
                        delete(userAuthEventDTO);
                        break;
                    case PATCH:
                    case CHANGE_PASSWORD:
                        patch(userAuthEventDTO);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void patch(UserAuthEventDTO user) {
        saveData(user);
    }

    @Override
    public void delete(UserAuthEventDTO user) {
        log.info("In delete method");
    }

    @Override
    public void logout(UserAuthEventDTO user) {
        saveData(user);
    }

    @Override
    public void login(UserAuthEventDTO user) {
        saveData(user);
    }

    @Override
    public void logoutAll(UserAuthEventDTO user) {
        saveData(user);
    }

    @Override
    public void signup(UserAuthEventDTO user) {
        saveData(user);
    }

    private boolean collectionExists() {
        var userCollectionExists = reactiveMongoTemplate.collectionExists(UserNotification.class).block();
        var restaurantCollectionExists = reactiveMongoTemplate.collectionExists(RestaurantNotification.class).block();
        var deliveryCollectionExists = reactiveMongoTemplate.collectionExists(DeliveryNotification.class).block();
        return userCollectionExists != null && userCollectionExists
                && restaurantCollectionExists != null && restaurantCollectionExists
                && deliveryCollectionExists != null && deliveryCollectionExists;
    }

    private void saveData(UserAuthEventDTO user) {
        var metaData = metaDataRepository.findById(user.getUserId()).orElse(null);
        if (collectionExists() && (metaData == null || metaData.getLastActivity() != user.getActivity())) {
            switch (user.getUserType()) {
                case USER:
                    userNotificationRepository.save(new UserNotification(user.getActivity().name(), user.getUserId())).subscribe(result ->
                            log.info("User Entity has been saved: {}", result)
                    );
                    break;
                case RESTAURANT:
                    restaurantNotificationRepository.save(new RestaurantNotification(user.getActivity().name(), user.getUserId())).subscribe(result ->
                            log.info("Restaurant Entity has been saved: {}", result)
                    );
                    break;
                case DELIVERY:
                    deliveryNotificationRepository.save(new DeliveryNotification(user.getActivity().name(), user.getUserId())).subscribe(result ->
                            log.info("Delivery Entity has been saved: {}", result)
                    );
                    break;
                default:
                    break;
            }
            metaDataRepository.save(new MetaData(user.getUserId(), user.getActivity()));
        }
    }
}
