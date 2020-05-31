package com.dictionary.server.dictionary.oxford.service;

import com.dictionary.server.auth.UserFacade;
import com.dictionary.server.dictionary.oxford.mapper.OxfordDefinitionMapper;
import com.dictionary.server.dictionary.oxford.OxfordDictionaryFacade;
import com.dictionary.server.dictionary.oxford.model.MongoOxfordDefinition;
import com.dictionary.server.library.exception.DictionaryParseException;
import com.dictionary.server.library.exception.UserDictionaryLimitException;
import com.dictionary.server.library.exception.WordIsNotCorrectException;
import com.dictionary.server.library.model.oxford.entry.OxfordApiResponse;
import com.dictionary.server.library.repository.MongoDefinitionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OxfordServiceImpl implements OxfordService {

    @Value("${application.properties.demo-limit}")
    private Integer APPLICATION_DEMO_LIMIT;

    private MongoDefinitionRepository mongoDefinitionRepository;
    private UserFacade userFacade;

    private OxfordDictionaryFacade oxfordDictionaryFacade;

    @Autowired
    OxfordServiceImpl(MongoDefinitionRepository mongoDefinitionRepository, UserFacade userFacade, OxfordDictionaryFacade oxfordDictionaryFacade) {
        this.mongoDefinitionRepository = mongoDefinitionRepository;
        this.userFacade = userFacade;
        this.oxfordDictionaryFacade = oxfordDictionaryFacade;
    }

    @Override
    public List<String> getUserDictionary(String username) {
        return userFacade.getDictionary(username);
    }

    @Override
    public ResponseEntity deleteUserDictionaryWord(String username, String word) {
        List<String> userDictionary = userFacade.getDictionary(username);
        userDictionary.remove(word);
        userFacade.setDictionary(username, userDictionary);
        return ResponseEntity.ok("Book was deleted from user library's dictionary");
    }

    @Override
    public MongoOxfordDefinition getWordDefinition(String username, String word) { // TODO. Refactor. Duplicated code. Not readable for me.
        if (!isWordValid(word)) {
            throw new WordIsNotCorrectException("Word should contains only letters!");
        }

        Integer demoLimit = userFacade.getDemoLimit(username);
        if (demoLimit >= APPLICATION_DEMO_LIMIT && !userFacade.isPremium(username)) {
            throw new UserDictionaryLimitException("User has a max limit of searching words. Show to him DEMO layout.");
        }

        MongoOxfordDefinition mongoOxfordDefinitionResponse = mongoDefinitionRepository.findByWord(word);

        if (mongoOxfordDefinitionResponse != null) {
            List<String> userDictionary = userFacade.getDictionary(username);
            if (!userDictionary.contains(word)) {
                userDictionary.add(word);
                userFacade.setDictionary(username, userDictionary);
                userFacade.setDemoLimit(username, demoLimit + 1);
            }
            return mongoOxfordDefinitionResponse;
        }

        ResponseEntity<OxfordApiResponse> oxfordResponse = oxfordDictionaryFacade.getWordDefinition(word);
        // mapper is here.

        if (oxfordResponse.getBody() != null && oxfordResponse.getStatusCode() == HttpStatus.OK) {
            List<String> userDictionary = userFacade.getDictionary(username);
            if (!userDictionary.contains(word)) {
                userDictionary.add(word);
                userFacade.setDictionary(username, userDictionary);
                userFacade.setDemoLimit(username, demoLimit + 1);
            }

            MongoOxfordDefinition mongoOxfordDefinition;
            try {
                mongoOxfordDefinition = OxfordDefinitionMapper.toMongoDefinition(oxfordResponse.getBody());
                mongoOxfordDefinition.setWord(word);
                mongoDefinitionRepository.save(mongoOxfordDefinition);
            } catch (NullPointerException ex) {
                log.error("Mongo definition is null! Temporary solution."); // TODO. Temporary solution for test purpose;
                return null;
            }
            return mongoOxfordDefinition;
        } else {
            log.error("Unexpected problem with parsing response from publiclibrary repo. Word: [" + word, "], response: " + oxfordResponse);
            throw new DictionaryParseException("Unexpected problem with parsing response from publiclibrary repo. Please contact admin.");
        }
    }

    private boolean isWordValid(String word) {
        return word.matches("[a-zA-Z]+");
    }

}
