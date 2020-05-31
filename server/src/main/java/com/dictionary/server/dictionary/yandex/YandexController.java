package com.dictionary.server.dictionary.yandex;

import com.dictionary.server.dictionary.yandex.YandexService;
import com.dictionary.server.dictionary.yandex.model.MongoYandexTranslation;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/yandex")
public class YandexController {

    private YandexService yandexService;

    @Autowired
    YandexController(YandexService yandexService) {
        this.yandexService = yandexService;
    }


    @GetMapping("/translate/{text}")
    @ApiOperation("Get translation from Yandex")
    public ResponseEntity<String> getTextDefinition(@PathVariable String text, Principal principal, @RequestHeader("Authorization") String authHeader) {
        String username = principal.getName();
        return ResponseEntity.ok(yandexService.getTranslation(username, text));
    }

    @GetMapping("/words")
    @ApiOperation("Get user yandex's dictionary")
    public ResponseEntity<MongoYandexTranslation> getUserDictionary(Principal principal, @RequestHeader("Authorization") String authHeader) {
        String username = principal.getName();

        MongoYandexTranslation userDictionary = yandexService.getUserDictionary(username);
        if (Objects.isNull(userDictionary)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(yandexService.getUserDictionary(username));
        }
    }

    @DeleteMapping("/dictionary/delete/{text}")
    @ApiOperation("Delete word from user library's dictionary")
    public ResponseEntity deleteWord(@PathVariable String text, Principal principal, @RequestHeader("Authorization") String authHeader) {
        String username = principal.getName();
        return yandexService.deleteSentence(username, text);
    }
}
