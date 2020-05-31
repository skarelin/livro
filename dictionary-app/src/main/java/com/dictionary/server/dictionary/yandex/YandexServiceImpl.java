package com.dictionary.server.dictionary.yandex;

import com.dictionary.server.dictionary.yandex.model.MongoYandexTranslation;
import com.dictionary.server.dictionary.yandex.model.YandexTranslation;
import com.dictionary.server.library.exception.WordIsNotCorrectException;
import com.dictionary.server.library.repository.MongoYandexTranslateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class YandexServiceImpl implements YandexService {

    @Value("${application.properties.demo-limit}")
    private Integer APPLICATION_DEMO_LIMIT;

    private YandexApiCaller yandexApiCaller;
    private MongoYandexTranslateRepository mongoYandexTranslateRepository;

    @Autowired
    public YandexServiceImpl(YandexApiCaller yandexApiCaller, MongoYandexTranslateRepository mongoYandexTranslateRepository) {
        this.yandexApiCaller = yandexApiCaller;
        this.mongoYandexTranslateRepository = mongoYandexTranslateRepository;
    }

    @Override
    public String getTranslation(String username, String text) {
        if (Objects.isNull(text) || text.length() <= 0) {
            throw new WordIsNotCorrectException("[Yandex]: Uncorrect text for translation: " + text);
        }

        String translation = yandexApiCaller.getTranslation(text);
        log.info("User " + username + " is getting translate text from Yandex: " + text);

        Optional<MongoYandexTranslation> mongoYandexTranslationOptional = mongoYandexTranslateRepository.findByUsername(username);

        if (mongoYandexTranslationOptional.isPresent()) {
            MongoYandexTranslation mongoYandexTranslation = mongoYandexTranslationOptional.get();

            List<YandexTranslation> userYandexTranslationList = mongoYandexTranslation.getYandexTranslationList();
            userYandexTranslationList.add(YandexTranslation.builder()
                    .englishOriginal(text)
                    .russianTranslation(translation)
                    .build());
            mongoYandexTranslation.setYandexTranslationList(userYandexTranslationList);

            log.info("Saving '" + text + "' for user library. User: " + username);
            mongoYandexTranslateRepository.save(mongoYandexTranslation);
        } else {
            MongoYandexTranslation mongoYandexTranslation = MongoYandexTranslation.builder()
                    .username(username)
                    .yandexTranslationList(Collections.singletonList(YandexTranslation
                            .builder()
                            .englishOriginal(text)
                            .russianTranslation(translation)
                            .build())).build();
            log.info("Saving '" + text + "' for user library. User: " + username);
            mongoYandexTranslateRepository.save(mongoYandexTranslation);
        }

        return translation;
    }

    public MongoYandexTranslation getUserDictionary(String username) {
        Optional<MongoYandexTranslation> dictionaryOptional = mongoYandexTranslateRepository.findByUsername(username);
        log.info("User " + username + " get dictionary");
        return dictionaryOptional.orElse(null);
    }

    public ResponseEntity deleteSentence(String username, String text) {
        Optional<MongoYandexTranslation> dictionaryOptional = mongoYandexTranslateRepository.findByUsername(username);

        if (dictionaryOptional.isPresent()) {
            MongoYandexTranslation mongoYandexTranslation = dictionaryOptional.get();

            List<YandexTranslation> newDictionary = mongoYandexTranslation.getYandexTranslationList().stream().filter(e -> !text.equals(e.getEnglishOriginal())).collect(Collectors.toList());
            mongoYandexTranslation.setYandexTranslationList(newDictionary);

            mongoYandexTranslateRepository.save(mongoYandexTranslation);

            log.info("User " + username + " delete text from his dictionary: " + text);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
