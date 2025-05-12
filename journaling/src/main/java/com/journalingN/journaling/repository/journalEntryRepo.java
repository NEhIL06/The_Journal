package com.journalingN.journaling.repository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.journalingN.journaling.entity.JournalEntry;


public interface journalEntryRepo extends MongoRepository<JournalEntry,ObjectId> {


}
