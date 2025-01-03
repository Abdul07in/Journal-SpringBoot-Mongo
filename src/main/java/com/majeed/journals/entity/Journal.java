package com.majeed.journals.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Document(collection = "journal_entries")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Journal {

    @Id
    private ObjectId id;

    @NonNull
    private String title;
    private String content;
    private LocalDateTime dateTime;

    public String getId() {
        return id.toString();
    }

    public String getDateTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a"));
    }


}
