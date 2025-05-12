package com.journalingN.journaling.scheduler;
import com.journalingN.journaling.cache.AppCache;
import com.journalingN.journaling.eNum.Sentiments;
import com.journalingN.journaling.entity.JournalEntry;
import com.journalingN.journaling.entity.User;
import com.journalingN.journaling.model.SentimentData;
import com.journalingN.journaling.repository.UserRepoImpl;
import com.journalingN.journaling.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import com.journalingN.journaling.services.sentimentAnalysis;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepoImpl userRepo;

    @Autowired
    private sentimentAnalysis sentimentAnalysis;

    @Autowired
    private AppCache appCache;

//    @Autowired
//    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendMail(){
        List<User> users = userRepo.getUserForSA();
        for(User user:users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiments> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(x->x.getSentiment()).toList();
            //String entry = String.join("",filteredEntries);
           // String sentiment = sentimentAnalysis.getSentiment(entry);
            //emailService.mailSender(user.getEmail(), "Sentiment Analysis",sentiment);

            Map<Sentiments,Integer> sentimentCount = new HashMap<>();
            for(Sentiments sentiments:filteredEntries){
                if(sentiments!=null){
                    sentimentCount.put(sentiments,sentimentCount.getOrDefault(sentiments,0)+1);
                }
            }
            Sentiments mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiments,Integer> entry : sentimentCount.entrySet()){
                if(entry.getValue()>maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();

                }
            }
            if(mostFrequentSentiment!=null){
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFrequentSentiment).build();
//                try{
//                    kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
//                }catch (Exception e){
//                    emailService.mailSender(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
//                }
                emailService.mailSender(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void refreshApi(){
        appCache.init();
    }
}
