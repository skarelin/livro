package com.dictionary.server.library.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("book")
@Data
@ToString
public class MongoBook {
    @Id
    private String id;
    private String title;
    private List<String> pages;
}
