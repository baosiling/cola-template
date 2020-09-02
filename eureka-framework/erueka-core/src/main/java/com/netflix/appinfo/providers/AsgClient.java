package com.netflix.appinfo.providers;

public interface AsgClient {
//    boolean isASGEnabled(InstanceInfo instanceInfo);

    void setStatus(String asgName, boolean enabled);
}
