package com.journalingN.journaling.repository;


import com.journalingN.journaling.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public record userRepoImplTests(UserRepoImpl userRepoImpl) {

    @Autowired
    public userRepoImplTests(UserRepoImpl userRepoImpl) {  //The constructor
        this.userRepoImpl = userRepoImpl;
    }

    @Test
    public void testUserRepoImpl(){
        Assertions.assertNotNull(userRepoImpl.getUserForSA());
    }
}
