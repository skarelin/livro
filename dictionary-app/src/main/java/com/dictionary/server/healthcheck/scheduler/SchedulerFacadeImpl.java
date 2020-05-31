package com.dictionary.server.healthcheck.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SchedulerFacadeImpl implements SchedulerFacade {

    @Override
    public boolean isServicesAlive() {
        return HealthCheckProperties.IS_SERVICES_ALIVE;
    }
}
