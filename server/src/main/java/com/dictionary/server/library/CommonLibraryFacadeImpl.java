package com.dictionary.server.library;

import com.dictionary.server.library.exception.LibraryException;
import com.dictionary.server.library.model.MysqlBook;
import com.dictionary.server.library.model.dto.MysqlBookDTO;
import com.dictionary.server.library.publiclibrary.PublicLibraryService;
import com.dictionary.server.library.repository.BookRepository;
import com.dictionary.server.library.userlibrary.UserLibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CommonLibraryFacadeImpl implements CommonLibraryFacade {

    private UserLibraryService userLibraryService;
    private PublicLibraryService publicLibraryService;
    private BookRepository bookRepository;

    private static final Integer COUNT_OF_PUBLIC_BOOKS = 5;

    @Autowired
    CommonLibraryFacadeImpl(UserLibraryService userLibraryService, PublicLibraryService publicLibraryService, BookRepository bookRepository) {
        this.userLibraryService = userLibraryService;
        this.publicLibraryService = publicLibraryService;
        this.bookRepository = bookRepository;
    }

    @Override
    public ResponseEntity saveBook(String username, String bookTitle, MultipartFile book) {
        return userLibraryService.saveBook(username, bookTitle, book);
    }

    @Override
    public ResponseEntity addPublicBookForUser(String username, Integer publicId) {
        return publicLibraryService.addBookForUser(username, publicId);
    }

    @Override
    public void downloadBook(String username, HttpServletResponse response, UUID bookId) {
        MysqlBook book = bookRepository.findById(bookId).orElseThrow(() -> new LibraryException("No book for id: " + bookId + " for user: " + username));

        log.info("Username " + username + " downloading his books");
        switch (book.getLibrarySourceType()) {
            case USER: {
                userLibraryService.downloadBook(response, book);
                break;
            }
            case PUBLIC: {
                publicLibraryService.downloadBook(response, book);
                break;
            }
            default: {
                throw new LibraryException("Common library cannot determine library source type. Source type: " + book.getLibrarySourceType());
            }
        }
    }

    @Override
    public ResponseEntity deleteBook(String username, UUID bookId) {
        return userLibraryService.deleteBook(username, bookId);
    }

    @Override
    public List<MysqlBookDTO> getUserBooks(String username) {
        return userLibraryService.getUserBooks(username);
    }
}
