package com.netflix.discovery.shared;

/**
 * @description: The class that wraps all the registry information returned by eureka server.
 * <p>
 * Note that the registry information is fetched from eureka server as specified
 * in {@link com.netflix.discovery.EurekaClientConfig#getRegistryFetchIntervalSeconds()}.Once the
 * information is fetched it is shuffled and also filtered for instances with
 * {@link com.netflix.appinfo.InstanceInfo.InstanceStatus#UP} status as specified by the configuration
 * {@link com.netflix.discovery.EurekaClientConfig#shouldFilterOnlyUpInstances()}
 * </p>
 * @author: wangzhx
 * @create: 2020-09-06 22:36
 */
public class Applications {
}