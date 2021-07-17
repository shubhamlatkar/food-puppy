package com.foodgrid.notification.command.model.aggregate;

import com.foodgrid.common.utility.UserActivities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {
    @Id
    private String id;
    private UserActivities lastActivity;
}
