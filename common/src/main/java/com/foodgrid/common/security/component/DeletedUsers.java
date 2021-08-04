package com.foodgrid.common.security.component;

import com.foodgrid.common.security.model.aggregate.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletedUsers {
    private Set<User> users;

    public void addUser(User user) {
        if (users == null)
            users = new HashSet<>();
        users.add(user);
    }
}
