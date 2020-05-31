package com.dictionary.server.dictionary.yandex;

import com.dictionary.server.dictionary.yandex.model.MongoYandexTranslation;
import org.springframework.http.ResponseEntity;

public interface YandexService {
    String getTranslation(String username, String text);
    MongoYandexTranslation getUserDictionary(String username);
    ResponseEntity deleteSentence(String username, String text);
}
