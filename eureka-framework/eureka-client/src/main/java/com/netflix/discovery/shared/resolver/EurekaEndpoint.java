package com.netflix.discovery.shared.resolver;

public interface EurekaEndpoint extends Comparable<Object> {

    String getServiceUrl();

    /**
     * @deprecated use {@link #getNetworkAddress()}
     */
    @Deprecated
    String getHostName();

    String getNetworkAddress();

    int getPort();

    boolean isSecure();

    String getRelativeUri();
}
