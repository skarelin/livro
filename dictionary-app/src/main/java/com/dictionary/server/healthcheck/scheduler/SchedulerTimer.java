package com.dictionary.server.healthcheck.scheduler;

import com.dictionary.server.healthcheck.scheduler.service.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
// TODO. Temporary solution.
// TODO. First check after 30 mins. Temporary logic for test purpose.
public class SchedulerTimer {

    private SchedulerService schedulerService;

    @Autowired
    public SchedulerTimer(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Scheduled(cron = "0 */30 * * * *")
    public void hourlyHealthChecker() {
        boolean mysqlConnection = schedulerService.checkMysqlConnection();
        boolean mongoConnection = schedulerService.checkMongoConnection();
        boolean keycloakConnection = schedulerService.checkKeycloakConnection();

        if (mysqlConnection && mongoConnection && keycloakConnection) {
            log.info("[HealthCheck]: Services work great.");
            HealthCheckProperties.IS_SERVICES_ALIVE = true;
        } else {
            HealthCheckProperties.IS_SERVICES_ALIVE = false;
            log.error("[HealthCheck]: Application is not stable:");
            log.error("[HealthCheck]: Mysql connection: " + mysqlConnection);
            log.error("[HealthCheck]: Mongo connection: " + mongoConnection);
            log.error("[HealthCheck]: Keycloak connection: " + keycloakConnection);
        }
    }
}
