package com.dictionary.server.library.repository;

import com.dictionary.server.library.model.MongoBook;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoBookRepository extends MongoRepository<MongoBook, String> {
    MongoBook findByTitle(String title);
}
