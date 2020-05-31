package com.dictionary.server.healthcheck.scheduler.service;

import com.dictionary.server.auth.UserFacade;
import com.dictionary.server.auth.model.User;
import com.dictionary.server.dictionary.yandex.model.MongoYandexTranslation;
import com.dictionary.server.keycloak.KeycloakFacade;
import com.dictionary.server.library.repository.MongoYandexTranslateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SchedulerServiceImpl implements SchedulerService {

    // TODO. Repository cannot be here. Temporary solution.
    private UserFacade userFacade;
    private KeycloakFacade keycloakFacade;
    private MongoYandexTranslateRepository mongoYandexTranslateRepository;

    @Autowired
    public SchedulerServiceImpl(UserFacade userFacade, KeycloakFacade keycloakFacade, MongoYandexTranslateRepository mongoYandexTranslateRepository) {
        this.userFacade = userFacade;
        this.keycloakFacade = keycloakFacade;
        this.mongoYandexTranslateRepository = mongoYandexTranslateRepository;
    }

    @Override
    public boolean checkMysqlConnection() {
        User testUser = userFacade.getByUsername(KeycloakFacade.TEST_USERNAME);
        return testUser != null;
    }

    @Override
    public boolean checkKeycloakConnection() {
        String token = keycloakFacade.testConnection();
        log.debug("[HealthCheck-debug]: keycloak token = " + token);
        return token != null;
    }

    @Override
    public boolean checkMongoConnection() {
        Optional<MongoYandexTranslation> testDefinition = mongoYandexTranslateRepository.findByUsername(KeycloakFacade.TEST_USERNAME.toLowerCase());
        log.debug("[HealthCheck-debug]: mongo definition = " + testDefinition);
        return testDefinition.isPresent();
    }
}
