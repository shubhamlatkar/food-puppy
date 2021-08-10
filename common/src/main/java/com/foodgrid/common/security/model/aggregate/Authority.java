package com.foodgrid.common.security.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    @Id
    private String id;
    private String name;

    public Authority(String name) {
        this.name = name;
    }
}

