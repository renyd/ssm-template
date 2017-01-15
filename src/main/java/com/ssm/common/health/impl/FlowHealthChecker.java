package com.ssm.common.health.impl;

import com.ssm.common.health.HealthChecker;

/**
 * Created by Domg on 2017/1/15. 流量开关检查
 */
public class FlowHealthChecker implements HealthChecker {

    public boolean checkHealth() {
        return true;
    }
}
