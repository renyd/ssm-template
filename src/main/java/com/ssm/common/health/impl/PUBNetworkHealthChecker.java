package com.ssm.common.health.impl;

import com.ssm.common.health.HealthChecker;

/**
 * Created by Domg on 2017/1/15. 公网健康检查
 */
public class PUBNetworkHealthChecker implements HealthChecker {

    public boolean checkHealth() {
        return true;
    }
}
