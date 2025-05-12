package com.journalingN.journaling.services;

import com.journalingN.journaling.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {
    @Autowired
    private EmailService emailService;

//    @KafkaListener(topics = "weekly-sentiments" , groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData){mailSender(sentimentData);}

    private void mailSender(SentimentData sentimentData){
        emailService.mailSender(sentimentData.getEmail(),"Sentiment data for the past week",sentimentData.getSentiment());
    }
}
