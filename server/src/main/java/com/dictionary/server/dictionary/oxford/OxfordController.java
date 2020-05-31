package com.dictionary.server.dictionary.oxford;

import com.dictionary.server.dictionary.oxford.model.MongoOxfordDefinition;
import com.dictionary.server.dictionary.oxford.service.OxfordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class OxfordController {

    private OxfordService oxfordService;

    @Autowired
    OxfordController(OxfordService oxfordService) {
        this.oxfordService = oxfordService;
    }

    @GetMapping("/definition/{word}")
    @ApiOperation("Get word's definition")
    public MongoOxfordDefinition getWordDefinition(@PathVariable String word, Principal principal, @RequestHeader("Authorization") String authHeader) {
        String username = principal.getName();
        return oxfordService.getWordDefinition(username, word);
    }

    @DeleteMapping("/words/delete/{word}")
    @ApiOperation("Delete word from user library's dictionary")
    public ResponseEntity deleteWord(@PathVariable String word, Principal principal, @RequestHeader("Authorization") String authHeader) {
        String username = principal.getName();
        return oxfordService.deleteUserDictionaryWord(username, word);
    }

    @GetMapping("/words")
    @ApiOperation("Get user library's dictionary")
    public List<String> getUserDictionary(Principal principal, @RequestHeader("Authorization") String authHeader) {
        String username = principal.getName();
        return oxfordService.getUserDictionary(username);
    }
}
