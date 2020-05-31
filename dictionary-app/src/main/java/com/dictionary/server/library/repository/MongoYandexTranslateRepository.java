package com.dictionary.server.library.repository;

import com.dictionary.server.dictionary.yandex.model.MongoYandexTranslation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoYandexTranslateRepository extends MongoRepository<MongoYandexTranslation, String> {
    Optional<MongoYandexTranslation> findByUsername(String username);
}
