package com.foodgrid.user.query.internal.repository;

import com.foodgrid.user.query.internal.model.aggregate.AddressQueryModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AddressQueryRepository extends MongoRepository<AddressQueryModel, String> {
    Optional<List<AddressQueryModel>> findByUserId(String userId);
}

