package com.foodgrid.common.security.repository;

import com.foodgrid.common.security.model.aggregate.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthorityRepository extends MongoRepository<Authority, String> {
    Optional<Authority> findByName(String name);
}
