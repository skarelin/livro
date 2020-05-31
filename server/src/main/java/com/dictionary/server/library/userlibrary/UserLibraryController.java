package com.dictionary.server.library.userlibrary;

import com.dictionary.server.auth.UserFacade;
import com.dictionary.server.library.CommonLibraryFacade;
import com.dictionary.server.library.model.LibrarySourceType;
import com.dictionary.server.library.model.dto.MysqlBookDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user-library")
@Slf4j
public class UserLibraryController {

    private CommonLibraryFacade commonLibraryFacade;

    @Autowired
    public UserLibraryController(CommonLibraryFacade commonLibraryFacade) {
        this.commonLibraryFacade = commonLibraryFacade;
    }

    @PostMapping(value = "/save/{title}")
    @ResponseBody
    @ApiOperation("Save user library's book to application library")
    public ResponseEntity uploadBook(Principal principal, @RequestParam("file") MultipartFile file, @PathVariable("title") String bookTitle, @RequestHeader("Authorization") String authHeader) { // TODO. Read about ResponseEntity answer
        String username = UserFacade.getUsernameByPrincipal(principal);

        return commonLibraryFacade.saveBook(username, bookTitle, file);
    }

    @GetMapping(value = "/download/{bookId}")
    @ApiOperation("Download user library's book by bookId")
    public void downloadBook(Principal principal, HttpServletResponse httpServletResponse,
                             @PathVariable("bookId") UUID bookId,
                             @RequestHeader("Authorization") String authHeader) {
        String username = UserFacade.getUsernameByPrincipal(principal);
        commonLibraryFacade.downloadBook(username, httpServletResponse, bookId);
    }

    @DeleteMapping(value = "/delete/{bookId}")
    @ApiOperation("Delete book from user's library")
    public ResponseEntity deleteBook(Principal principal, @PathVariable("bookId") UUID bookId, @RequestHeader("Authorization") String authHeader) {
        String username = UserFacade.getUsernameByPrincipal(principal);
        return commonLibraryFacade.deleteBook(username, bookId);
    }

    @GetMapping(value = "/user/books")
    @ApiOperation("Get user library's books from the application library")
    public ResponseEntity getUserBooks(Principal principal, @RequestHeader("Authorization") String authHeader) {
        String username = UserFacade.getUsernameByPrincipal(principal);

        List<MysqlBookDTO> books = commonLibraryFacade.getUserBooks(username);
        return ResponseEntity.ok().body(books);
    }
}
