package com.dictionary.server.healthcheck.scheduler.service;

public interface SchedulerService {
    boolean checkMysqlConnection();
    boolean checkMongoConnection();
    boolean checkKeycloakConnection();
}
