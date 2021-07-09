package com.foodgrid.notification.command.repository;

import com.foodgrid.notification.command.model.aggregate.MetaData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetaDataRepository extends MongoRepository<MetaData, String> {
}
