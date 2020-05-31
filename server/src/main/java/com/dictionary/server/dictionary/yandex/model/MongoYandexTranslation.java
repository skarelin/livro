package com.dictionary.server.dictionary.yandex.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("mongoYandexTranslation")
@Data
@Builder
public class MongoYandexTranslation {

    @Id
    private String username;
    List<YandexTranslation> yandexTranslationList;
}
