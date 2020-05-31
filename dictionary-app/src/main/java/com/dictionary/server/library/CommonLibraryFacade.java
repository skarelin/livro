package com.dictionary.server.library;

import com.dictionary.server.library.model.dto.MysqlBookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Component
public interface CommonLibraryFacade {
    ResponseEntity saveBook(String username, String bookTitle, MultipartFile book);

    ResponseEntity addPublicBookForUser(String username, Integer publicId);

    void downloadBook(String username, HttpServletResponse response, UUID bookId);

    ResponseEntity deleteBook(String username, UUID bookId);

    List<MysqlBookDTO> getUserBooks(String username);
}
