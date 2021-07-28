package com.foodgrid.user.command.internal.repository;

import com.foodgrid.user.command.internal.model.aggregate.AddressCommandModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AddressCommandRepository extends MongoRepository<AddressCommandModel, String> {
    Optional<List<AddressCommandModel>> findByUserId(String userId);
}
