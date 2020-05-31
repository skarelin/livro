package com.dictionary.server.dictionary.oxford.model;

import com.dictionary.server.library.model.oxford.DictionaryEntry;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("mongoOxfordDefinition")
@Data
@Builder
public class MongoOxfordDefinition {
    private String word;
    private List<DictionaryEntry> senses;
}
