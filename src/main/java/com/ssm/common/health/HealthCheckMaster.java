package com.ssm.common.health;

import com.ssm.common.constants.SSMApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Domg on 2017/1/15.
 */
public class HealthCheckMaster {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckMaster.class);

    private List<HealthChecker> checkerList = new ArrayList<HealthChecker>();


    @Scheduled(cron = "0/5 * * * * ?")
    public void check() {
        if (!CollectionUtils.isEmpty(checkerList)) {
            for (HealthChecker checker : checkerList) {
                if (!checker.checkHealth()) {
                    if (SSMApp.RUNNING) {
                        LOGGER.info("{} check health return false!", checker.getClass().getName());
                        SSMApp.RUNNING = false;
                    }
                    return;
                }
            }
        }
        if (!SSMApp.RUNNING) {
            LOGGER.info("SSM app status change to true.");
            SSMApp.RUNNING = true;
        }
    }

    public List<HealthChecker> getCheckerList() {
        return checkerList;
    }

    public void setCheckerList(List<HealthChecker> checkerList) {
        this.checkerList = checkerList;
    }
}
