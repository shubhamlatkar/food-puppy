package com.foodgrid.common.security.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private String id;
    private String name;
    private List<Authority> authorities;

    public Role(String name, List<Authority> authorities) {
        this.name = name;
        this.authorities = authorities;
    }
}
