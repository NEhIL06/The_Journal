package com.journalingN.journaling.repository;

import com.journalingN.journaling.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepoImpl {
    @Autowired
    private MongoTemplate mongotemplate;


    public List<User> getUserForSA(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Z|a-z]{2,6}$"));
        //query.addCriteria(Criteria.where("email").exists(true));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.orOperator(
//                Criteria.where("Email").exists(true),
//                Criteria.where("sentimentAnalysis").is(true))
//        );
        List<User> users;
        users = mongotemplate.find(query, User.class);
        return users;
    }
}
