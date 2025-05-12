package com.journalingN.journaling.entity;


import java.time.LocalDateTime;

import com.journalingN.journaling.eNum.Sentiments;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//this is a type of POJO
@Document
@Data
@NoArgsConstructor
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;

    private String content;

    private LocalDateTime date;

    private Sentiments sentiment;
}
