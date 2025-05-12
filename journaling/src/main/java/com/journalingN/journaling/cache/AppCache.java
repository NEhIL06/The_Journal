package com.journalingN.journaling.cache;

import com.journalingN.journaling.entity.configJournalAppEntity;
import com.journalingN.journaling.repository.configRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        weather_api;
    }

    @Autowired
    private configRepo configRepo;

    public Map<String, String> APP_CACHE = new HashMap<>();

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();// reinitialized in case of any changes in the Database
        List<configJournalAppEntity> all = configRepo.findAll();
        for(configJournalAppEntity configJournalAppEntity:all) {
            APP_CACHE.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }

}
