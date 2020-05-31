package com.dictionary.server.library.publiclibrary;

import com.dictionary.server.auth.UserFacade;
import com.dictionary.server.library.CommonLibraryFacade;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/public-library")
@Slf4j
public class PublicLibraryController {

    private CommonLibraryFacade commonLibraryFacade;

    @Autowired
    public PublicLibraryController(CommonLibraryFacade commonLibraryFacade) {
        this.commonLibraryFacade = commonLibraryFacade;
    }

    @PostMapping(value = "/save/{publicId}")
    @ApiOperation("Add public book for user's library")
    public ResponseEntity addPublicBookToUserLibrary(Principal principal,
                                                     @PathVariable("publicId") Integer publicId,
                                                     @RequestHeader("Authorization") String authHeader) {
        String username = UserFacade.getUsernameByPrincipal(principal);
        return commonLibraryFacade.addPublicBookForUser(username, publicId);
    }
}
