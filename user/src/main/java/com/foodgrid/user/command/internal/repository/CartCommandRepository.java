package com.foodgrid.user.command.internal.repository;

import com.foodgrid.user.command.internal.model.aggregate.CartCommandModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartCommandRepository extends MongoRepository<CartCommandModel, String> {
}
