package com.dictionary.server.library.publiclibrary;

import com.dictionary.server.library.model.MysqlBook;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface PublicLibraryService {
    void downloadBook(HttpServletResponse httpServletResponse, MysqlBook mysqlBook);
    ResponseEntity addBookForUser(String username, Integer publicId);
}
