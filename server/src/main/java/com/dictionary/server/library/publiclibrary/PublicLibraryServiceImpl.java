package com.dictionary.server.library.publiclibrary;

import com.dictionary.server.auth.UserFacade;
import com.dictionary.server.auth.model.User;
import com.dictionary.server.library.model.LibrarySourceType;
import com.dictionary.server.library.model.MysqlBook;
import com.dictionary.server.library.repository.BookRepository;
import com.dictionary.server.manager.file.FileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class PublicLibraryServiceImpl implements PublicLibraryService {

    private FileManager fileManager;
    private UserFacade userFacade;
    private BookRepository bookRepository;

    @Autowired
    PublicLibraryServiceImpl(FileManager fileManager, UserFacade userFacade, BookRepository bookRepository) {
        this.fileManager = fileManager;
        this.userFacade = userFacade;
        this.bookRepository = bookRepository;
    }

    @Override
    public void downloadBook(HttpServletResponse httpServletResponse, MysqlBook mysqlBook) {
        fileManager.createOutputStreamForPublicLibrary(httpServletResponse, mysqlBook.getFileName());
    }

    @Override
    public ResponseEntity addBookForUser(String username, Integer publicId) {
        User user = userFacade.getByUsername(username);

        MysqlBook mysqlBook = MysqlBook.builder()
                .publicId(publicId)
                .fileName(PublicBookIdConverter.toFileName(publicId))
                .title(PublicBookIdConverter.toBookName(publicId))
                .librarySourceType(LibrarySourceType.PUBLIC)
                .user(user)
                .build();
        MysqlBook saveBook = bookRepository.save(mysqlBook);
        log.info("Username + " + username + " added book to public library. MysqlBook UUID: " + mysqlBook.getId());
        return ResponseEntity.ok(saveBook);
    }
}
