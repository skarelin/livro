package com.dictionary.server.library.repository;

import com.dictionary.server.library.model.MysqlBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<MysqlBook, UUID> {
}
