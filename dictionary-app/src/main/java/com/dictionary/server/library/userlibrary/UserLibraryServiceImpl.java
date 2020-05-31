package com.dictionary.server.library.userlibrary;

import com.dictionary.server.auth.UserFacade;
import com.dictionary.server.auth.model.User;
import com.dictionary.server.library.exception.LibraryException;
import com.dictionary.server.library.exception.LibraryMaxLimitOfBooksException;
import com.dictionary.server.library.model.LibrarySourceType;
import com.dictionary.server.library.model.MysqlBook;
import com.dictionary.server.library.model.converter.MysqlBookConverter;
import com.dictionary.server.library.model.dto.MysqlBookDTO;
import com.dictionary.server.library.repository.BookRepository;
import com.dictionary.server.manager.file.FileManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserLibraryServiceImpl implements UserLibraryService {

    @Value("${library.max-books-for-user}")
    private Integer MAX_BOOKS_FOR_USER;

    private final UserFacade userFacade;
    private final BookRepository bookRepository;
    private final FileManagerImpl fileManager;

    @Autowired
    UserLibraryServiceImpl(UserFacade userFacade, BookRepository bookRepository, FileManagerImpl fileManager) {
        this.userFacade = userFacade;
        this.bookRepository = bookRepository;
        this.fileManager = fileManager;
    }

    @Override
    public ResponseEntity saveBook(String username, String bookTitle, MultipartFile file) {
        if (bookTitle == null || bookTitle.length() <= 0) {
            throw new LibraryException("Wrong book's title: " + bookTitle);
        }
        User user = userFacade.getByUsername(username);

        if (user.getMysqlBooks().size() >= MAX_BOOKS_FOR_USER) {
            throw new LibraryMaxLimitOfBooksException("User " + username + " has maximum count of books!");
        }

        MysqlBook mysqlBook = MysqlBook.builder()
                .title(bookTitle)
                .librarySourceType(LibrarySourceType.USER)
                .user(user)
                .build();
        MysqlBook savedMysqlBook = bookRepository.save(mysqlBook);

        String fullFileName = fileManager.saveFileForLibrary(file, savedMysqlBook.getId().toString());

        savedMysqlBook.setFileName(fullFileName);
        bookRepository.save(savedMysqlBook);
        log.info("User " + user.getUsername() + " saved book by id: " + savedMysqlBook.getId());
        return ResponseEntity.ok().build();
    }

    @Override
    public void downloadBook(HttpServletResponse response, final MysqlBook mysqlBook) {
        fileManager.createOutputStreamForUserLibrary(response, mysqlBook.getFileName());
    }

    @Override
    // TODO. Bad solution. Do better.
    public ResponseEntity deleteBook(String username, UUID bookId) {
        User user = userFacade.getByUsername(username);
        List<MysqlBook> userMysqlBooks = user.getMysqlBooks();
        for (MysqlBook book : userMysqlBooks) {
            if (book.getId().equals(bookId)) {
                String fileName = book.getFileName();
                bookRepository.delete(book);

                boolean isDeleted = fileManager.deleteFileForLibrary(fileName);
                return ResponseEntity.ok(isDeleted);
            }
        }
        return ResponseEntity.ok("User's book was deleted from the library");
    }

    @Override
    public List<MysqlBookDTO> getUserBooks(String username) {
        final List<MysqlBook> userMysqlBooks = userFacade.getByUsername(username).getMysqlBooks();
        List<MysqlBookDTO> result = new ArrayList<>();

        userMysqlBooks.forEach((mysqlBook) -> result.add(MysqlBookConverter.toDto(mysqlBook)));
        return result;
    }
}
