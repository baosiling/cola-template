package com.netflix.appinfo;

public interface HealthCheckHandler {

    InstanceInfo.InstanceStatus getStatus(InstanceInfo.InstanceStatus currentStatus);
}
