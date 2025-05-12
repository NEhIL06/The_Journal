package com.journalingN.journaling.repository;

import com.journalingN.journaling.entity.configJournalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface configRepo extends MongoRepository<configJournalAppEntity, ObjectId> {

}
