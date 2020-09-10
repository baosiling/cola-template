package com.netflix.discovery;

import com.google.inject.ImplementedBy;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Applications;
import com.netflix.discovery.shared.LookupService;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 * Define a simple interface over the current DiscoveryClient implementation.
 *
 * This interface does NOT try to clean up the current client interface for eureka 1.x.
 * Rather it tries to provide an easier transition path from eureka1.x to eureka2.x.
 *
 * EurekaClient API contracts are:
 * - provide the ability to get InstanceInfo(s) (in various different ways)
 * - provide the ability to get data about the local Client (known regions, own AZ etc)
 * - provide the ability to register and access the healthcheck handler for the client
 *
 */
@ImplementedBy(DiscoveryClient.class)
public interface EurekaClient extends LookupService {

    // =======================
    // getter for InstanceInfo
    // =======================

    /**
     * @param region the region that the Applications reside in
     * @return an {@link com.netflix.discovery.shared.Applications} for the matching regions.
     *          a null value is treated as the local region.
     */
    Applications getApplicationsForARegion(@Nullable String region);

    /**
     * Get all applications registered with a specific eureka service.
     *
     * @param serviceUrl The string representation of the service url.
     * @return The registry information containing all applications.
     */
    Applications getApplications(String serviceUrl);

    /**
     * Gets the list of instances matching the given VIP Address.
     * @param vipAddress the VIP address to match the instances for.
     * @param secure true if it is a secure vip address, false otherwise
     * @return the list of {@link InstanceInfo} objects matching the criteria
     */
    List<InstanceInfo> getInstancesByVipAddress(String vipAddress, boolean secure);

    /**
     * Gets the list of instances matching the given VIP Address in the passed region.
     *
     * @param vipAddress The VIP address to match the instances for.
     * @param secure true if it is a secure vip address, false otherwise
     * @param region region from which the instances are to be fetched. If <code>null</code> then local region is
     *               assumed.
     *
     * @return - The list of {@link InstanceInfo} objects matching the criteria, empty list if not instances found.
     */
    List<InstanceInfo> getInstancesByVipAddress(String vipAddress, boolean secure, @Nullable String region);

    /**
     * Gets the list of instances matching the given VIP Address and the given
     * application name if both of them are not null. If one of them is null,
     * then that criterion is completely ignored for matching instances.
     *
     * @param vipAddress The VIP address to match the instances for.
     * @param appName The applicationName to match the instances for.
     * @param secure true if it is a secure vip address, false otherwise.
     * @return - The list of {@link InstanceInfo} objects matching the criteria.
     */
    List<InstanceInfo> getInstancesByVipAddressAndAppName(String vipAddress, String appName, boolean secure);

    /**
     * @return in String form all regions (local + remote) that can be accessed by this client
     */
    Set<String> getAllKnownRegions();

    /**
     * @return the current self instance status as seen on the Eureka server.
     */
    InstanceInfo.InstanceStatus getInstanceRemoteStatus();

    /**
     * @deprecated see {@link com.netflix.discovery.endpoint.EndpointUtils} for replacement
     *
     * Get the list of all eureka service urls for the eureka client to talk to.
     *
     * @param zone the zone in which the client resides
     * @return The list of all eureka service urls for the eureka client to talk to.
     */
    @Deprecated
    List<String> getDiscoveryServiceUrls(String zone);

    /**
     * @deprecated see {@link com.netflix.discovery.endpoint.EndpointUtils} for replacement
     *
     * Get the list of all eureka service urls from properties file for the eureka client to talk to.
     *
     * @param instanceZone The zone in which the client resides
     * @param preferSameZone true if we have to prefer the same zone as the client, false otherwise
     * @return The list of all eureka service urls for the eureka client to talk to
     */
    @Deprecated
    List<String> getServiceUrlsFromConfig(String instanceZone, boolean preferSameZone);

    /**
     * @deprecated see {@link com.netflix.discovery.endpoint.EndpointUtils} for replacement
     *
     * Get the list of all eureka service urls from DNS for the eureka client to
     * talk to. The client picks up the service url from its zone and then fails over to
     * other zones randomly. If there are multiple servers in the same zone, the client once
     * again picks one randomly. This way the traffic will be distributed in the case of failures.
     *
     * @param instanceZone The zone in which the client resides.
     * @param preferSameZone true if we have to prefer the same zone as the client, false otherwise.
     * @return The list of all eureka service urls for the eureka client to talk to.
     */
    @Deprecated
    public List<String> getServiceUrlsFromDNS(String instanceZone, boolean preferSameZone);

    // ===========================
    // healthcheck related methods
    // ===========================

    /**
     * @deprecated Use {@link #registerHealthCheck(com.netflix.appinfo.HealthCheckHandler)} instead.
     *
     * Register {@link HealthCheckCallback} with the eureka client.
     *
     * Once registered, the eureka client will invoke the
     * {@link HealthCheckCallback} in intervals specified by
     * {@link EurekaClientConfig#getInstanceInfoReplicationIntervalSeconds()}.
     *
     * @param callback app specific healthcheck.
     */
//    @Deprecated
//    public void registerHealthCheckCallback(HealthCheckCallback callback);

    /**
     * Register {@link HealthCheckHandler} with the eureka client.
     *
     * Once registered, the eureka client will first make an onDemand update of the
     * registering instanceInfo by calling the newly registered healthcheck handler,
     * and subsequently invoke the {@link HealthCheckHandler} in intervals specified
     * by {@link EurekaClientConfig#getInstanceInfoReplicationIntervalSeconds()}.
     * @param healthCheckHandler app specific healthcheck handler.
     */
    void registerHealthCheck(HealthCheckHandler healthCheckHandler);

    /**
     * Register {@link EurekaEventListener} with the eureka client.
     *
     * Once registered, the eureka client will invoke {@link EurekaEventListener#onEvent(EurekaEvent)}
     * whenever there is a change in eureka client's internal state. Use this instead of
     * polling the client for changes.
     *
     * {@link EurekaEventListener#onEvent} is called from the context of an internal thread
     * and must therefore return as quickly as possible without blocking.
     * @param eventListener
     */
    void registerEventListener(EurekaEventListener eventListener);

    /**
     * Unregister a {@link EurekaEventListener} previous registered with {@link EurekaClient#registerEventListener}
     * or injected into the constructor of {@link DiscoveryClient}
     *
     * @param eventListener
     * @return True if removed otherwise false if the listener was never registered.
     */
    public boolean unregisterEventListener(EurekaEventListener eventListener);

    /**
     * @return the current registered healthcheck handler
     */
    public HealthCheckHandler getHealthCheckHandler();

    // ============
    // other method
    // ============

    /**
     * Shuts down Eureka Client. Also sends a deregistration request to the eureka server.
     */
    void shutdown();

    /**
     * @return the configuration of this eureka client
     */
    EurekaClientConfig getEurekaClientConfig();

    /**
     * @return the application info manger of this eureka client
     */
    ApplicationInfoManager getApplicationInfoManager();

}
