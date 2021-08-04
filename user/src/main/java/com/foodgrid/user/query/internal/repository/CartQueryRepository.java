package com.foodgrid.user.query.internal.repository;

import com.foodgrid.user.query.internal.model.aggregate.CartQueryModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartQueryRepository extends MongoRepository<CartQueryModel, String> {
}
