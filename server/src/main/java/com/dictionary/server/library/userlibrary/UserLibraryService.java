package com.dictionary.server.library.userlibrary;

import com.dictionary.server.library.model.MysqlBook;
import com.dictionary.server.library.model.dto.MysqlBookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;


public interface UserLibraryService {
    ResponseEntity saveBook(String username, String bookTitle, MultipartFile book);

    void downloadBook(HttpServletResponse response, MysqlBook mysqlBook);

    ResponseEntity deleteBook(String username, UUID bookId);

    List<MysqlBookDTO> getUserBooks(String username);
}
