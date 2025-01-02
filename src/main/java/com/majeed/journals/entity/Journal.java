package com.majeed.journals.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Document(collection = "journal_entries")
@Setter
@Getter()
@NoArgsConstructor
@AllArgsConstructor
public class Journal {

    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime dateTime;

    public String getId() {
        return id.toString();
    }


}
