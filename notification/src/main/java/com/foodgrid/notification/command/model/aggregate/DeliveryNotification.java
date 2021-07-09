package com.foodgrid.notification.command.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryNotification {

    @Id
    private String id;
    private String message;
    private String hostId;

    public DeliveryNotification(String message, String hostId) {
        this.message = message;
        this.hostId = hostId;
    }
}