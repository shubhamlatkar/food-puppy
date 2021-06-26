package com.foodgrid.common.security.repository;

import com.foodgrid.common.security.model.aggregate.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);
}
