package com.dictionary.server.library.repository;

import com.dictionary.server.dictionary.oxford.model.MongoOxfordDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDefinitionRepository extends MongoRepository<MongoOxfordDefinition, String> {
    MongoOxfordDefinition findByWord(String title);
}
