package com.journalingN.journaling.repository;

import com.journalingN.journaling.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired EmailService emailService;

    @Test
    void mailTest(){
        emailService.mailSender("nehil.1si22ci034@gmail.com","Testing","Ha ha this works ");
    }
}
