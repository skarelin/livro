package com.dictionary.server.dictionary.oxford.service;

import com.dictionary.server.dictionary.oxford.model.MongoOxfordDefinition;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OxfordService {
    MongoOxfordDefinition getWordDefinition(String username, String word);
    List<String> getUserDictionary(String username);
    ResponseEntity deleteUserDictionaryWord(String username, String word);
}
