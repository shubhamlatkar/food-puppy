package com.foodgrid.common.repository;


import com.foodgrid.common.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
