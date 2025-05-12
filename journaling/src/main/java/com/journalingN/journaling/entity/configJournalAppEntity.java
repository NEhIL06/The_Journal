package com.journalingN.journaling.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "journal_configuration_app")
@Data
@NoArgsConstructor
public class configJournalAppEntity {

    @Id
    private ObjectId objectId;

    @NonNull
    private String key;
    @NonNull
    private  String value;
}
