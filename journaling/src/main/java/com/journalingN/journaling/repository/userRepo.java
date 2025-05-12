package com.journalingN.journaling.repository;

import com.journalingN.journaling.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepo extends MongoRepository<User, ObjectId> {
        User findByUserName (String username);

        void deleteByUserName(String userName);

}
