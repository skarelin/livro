package com.dictionary.server.healthcheck;

import com.dictionary.server.healthcheck.scheduler.SchedulerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckServiceImpl implements HealthCheckService {

    private SchedulerFacade schedulerFacade;

    @Autowired
    public HealthCheckServiceImpl(SchedulerFacade schedulerFacade) {
        this.schedulerFacade = schedulerFacade;
    }

    @Override
    public Boolean isServicesAlive() {
        return schedulerFacade.isServicesAlive();
    }
}
