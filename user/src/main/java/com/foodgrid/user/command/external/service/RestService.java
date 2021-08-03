package com.foodgrid.user.command.external.service;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.common.payload.dto.response.GetItemResponse;
import com.foodgrid.user.command.internal.model.entity.ItemCommandModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RestService {
    private final RestTemplate restTemplate;

    @Autowired
    public RestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ItemCommandModel getItemShort(String restaurantId, String itemId) {
        ResponseEntity<GetItemResponse> shortItemResponse = restTemplate.getForEntity(
                "http://restaurant:8082/api/v1/public/short/menu/item?restaurantId=" + restaurantId + "&itemId=" + itemId,
                GetItemResponse.class
        );
        var shortItem = shortItemResponse.getBody();

        if (shortItem == null)
            throw new InternalServerErrorException("Error caught in getItemShort");

        log.info("Item received from restaurant service: {}", shortItem);
        return new ItemCommandModel(shortItem.getId(), shortItem.getName(), shortItem.getValue(), 1);

    }
}
