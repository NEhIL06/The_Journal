package com.journalingN.journaling.services;

import com.journalingN.journaling.entity.JournalEntry;
import com.journalingN.journaling.entity.User;
import com.journalingN.journaling.repository.journalEntryRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
public class  JournalEntryService {


    @Autowired
    private journalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    //Methods below
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);
//            if (user == null) {
//                log.error("User not found: {}", userName);
//                throw new RuntimeException("User not found: " + userName);
//            }

  //          log.info("User found: {}", user);
            journalEntry.setId(new ObjectId());
            journalEntry.setDate(LocalDateTime.now());

      //      log.info("Saving journal entry: {}", journalEntry);
            JournalEntry saved = journalEntryRepo.save(journalEntry);

            user.getJournalEntries().add(saved);
            userService.saveUser(user);
            log.info("Journal entry saved successfully!");
        } catch (RuntimeException e) {
            log.error("Error saving entry", e);
            throw e;
        }
    }


    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findByID(ObjectId id){
        return journalEntryRepo.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean bool = false;
        try {
            User user = userService.findByUserName(userName);
            bool = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (bool){
                userService.saveUser(user);
                journalEntryRepo.deleteById(id);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("An Error Occurred while Deleting the Item",e);
        }
        return bool;
    }
}
