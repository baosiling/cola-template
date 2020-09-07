package com.netflix.discovery.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.InstanceRegionChecker;
import com.netflix.discovery.provider.Serializer;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
@Serializer("com.netflix.discovery.converters.EntityBodyConverter")
@XStreamAlias("applications")
@JsonRootName("applications")
public class Applications {

    private static class VipIndexSupport {
        final AbstractQueue<InstanceInfo> instances = new ConcurrentLinkedQueue<>();
        final AtomicLong roundRobinIndex = new AtomicLong(0);
        final AtomicReference<List<InstanceInfo>> vipList = new AtomicReference<>(Collections.emptyList());

        public AtomicLong getRoundRobinIndex() {
            return roundRobinIndex;
        }

        public AtomicReference<List<InstanceInfo>> getVipList() {
            return vipList;
        }
    }

    private static final String STATUS_DELIMITER = "_";

    private String appsHashCode;
    private Long versionDelta;
    @XStreamImplicit
    private final AbstractQueue<Application> applications;
    private final Map<String, Application> appNameApplicationMap;
    private final Map<String, VipIndexSupport> virtualHostNameAppMap;
    private final Map<String, VipIndexSupport> secureVirtualHostNameAppMap;

    public Applications() {
        this(null, -1L, Collections.emptyList());
    }

    /**
     * Note that appHashCode and versionDelta key names are formatted in a
     * custom/configurable way.
     */
    @JsonCreator
    public Applications(@JsonProperty("appHashCode") String appsHashCode,
                        @JsonProperty("versionDelta") Long versionDelta,
                        @JsonProperty("application") List<Application> registeredApplications) {
        this.applications = new ConcurrentLinkedQueue<>();
        this.appNameApplicationMap = new ConcurrentHashMap<>();
        this.virtualHostNameAppMap = new ConcurrentHashMap<>();
        this.secureVirtualHostNameAppMap = new ConcurrentHashMap<>();
        this.appsHashCode = appsHashCode;
        this.versionDelta = versionDelta;

        for (Application app : registeredApplications) {
            this.addApplication(app);
        }

    }

    public void addApplication(Application app) {
        appNameApplicationMap.put(app.getName().toUpperCase(Locale.ROOT), app);
        addInstancesToVIPMaps(app, this.virtualHostNameAppMap, this.secureVirtualHostNameAppMap);
        applications.add(app);
    }

    /**
     * Gets the list of all registered <em>applications</em> from eureka.
     *
     * @return list containing all applications registered with eureka.
     */
    @JsonProperty("application")
    public List<Application> getRegisteredApplications() {
        return new ArrayList<>(this.applications);
    }

    /**
     * Gets the registered <em>application</em> for the given
     * application name.
     *
     * @param appName the application name for which the result need to be fetched.
     * @return the registered application for the given application
     * name.
     */
    public Application getRegisteredApplications(String appName) {
        return appNameApplicationMap.get(appName.toUpperCase(Locale.ROOT));
    }

    /**
     * Gets the list of <em>instances</em> associated to a virtual host name.
     *
     * @param virtualHostName the virtual hostname for which the instances need to be
     *                        returned.
     * @return list of <em>instances</em>.
     */
    public List<InstanceInfo> getInstancesByVirtualHostName(String virtualHostName) {
        return Optional.ofNullable(this.virtualHostNameAppMap.get(virtualHostName.toUpperCase(Locale.ROOT)))
                .map(VipIndexSupport::getVipList)
                .map(AtomicReference::get)
                .orElseGet(Collections::emptyList);
    }

    public List<InstanceInfo> getInstanceBySecureVirtualHostName(String secureVirtualHostName) {
        return Optional.ofNullable(this.secureVirtualHostNameAppMap.get(secureVirtualHostName.toUpperCase(Locale.ROOT)))
                .map(VipIndexSupport::getVipList)
                .map(AtomicReference::get)
                .orElseGet(Collections::emptyList);
    }

    /**
     * @return a weakly consistent size of the number of instances in all the applications
     */
    public int size() {
        return applications.stream().mapToInt(Application::size).sum();
    }

    @Deprecated
    public void setVersion(Long version) {
        this.versionDelta = version;
    }

    @Deprecated
    @JsonIgnore // Handled directly due to legacy name formatting
    public Long getVersion() {
        return this.versionDelta;
    }

    /**
     * Used by the eureka server. Not for external use.
     *
     * @param hashCode the hash code to assign for this app collection
     */
    public void setAppsHashCode(String hashCode) {
        this.appsHashCode = hashCode;
    }

    /**
     * Used by the eureka server. Not for external use.
     *
     * @return the string indicating hashcode based on the applications stored.
     */
    @JsonIgnore //handled directly due to legacy name formatting
    public String getAppsHashCode() {
        return appsHashCode;
    }

    /**
     * Gets the hash code for this <em>applications</em> instance. used for
     * comparison of instances between eureka and eureka client.
     *
     * @return the internal hash code representation indicating the information
     * about the instances.
     */
    @JsonIgnore
    public String getReconcileHashCode() {
        TreeMap<String, AtomicInteger> instanceCountMap = new TreeMap<>();
        populateInstanceCountMap(instanceCountMap);
        return getReconcileHashCode(instanceCountMap);
    }

    /**
     * Populates the provided instance count map. The instance count map is used
     * as part of the general app list synchronization mechanism.
     *
     * @param instanceCountMap
     */
    public void populateInstanceCountMap(Map<String, AtomicInteger> instanceCountMap) {
        for (Application app : this.getRegisteredApplications()) {
            for (InstanceInfo info : app.getInstanceAsIsFromEureka()) {
                AtomicInteger instanceCount = instanceCountMap.computeIfAbsent(info.getStatus().name(),
                        k -> new AtomicInteger(0));
                instanceCount.incrementAndGet();
            }
        }
    }

    /**
     * Gets the reconciliation hashcode. The hashcode is used to determine whether
     * the applications list has changed since the last time it was acquired.
     *
     * @param instanceCountMap the instance count map to use for generating the hash
     * @return the hash code for this instance
     */
    public static String getReconcileHashCode(Map<String, AtomicInteger> instanceCountMap) {
        StringBuilder reconcileHashCode = new StringBuilder(75);
        for (Map.Entry<String, AtomicInteger> mapEntry : instanceCountMap.entrySet()) {
            reconcileHashCode.append(mapEntry.getKey()).append(STATUS_DELIMITER)
                    .append(mapEntry.getValue().get()).append(STATUS_DELIMITER);
        }
        return reconcileHashCode.toString();
    }

    /**
     * Shuffles the provided instances so that they will not always be returned
     * in the same order.
     *
     * @param filterUpInstances
     */
    public void shuffleInstances(boolean filterUpInstances) {
        shuffleInstances(filterUpInstances, false, null, null, null);
    }

    /**
     * Shuffles a whole region that the instances will not always be returned in the same order.
     * @param remoteRegionRegistry the map of remote region names to their registries.
     * @param clientConfig the {@link EurekaClientConfig}, whose setting will be used to
     *                     determine whether to filter to only UP instances
     * @param instanceRegionChecker the instance region checker
     */
    public void shuffleAndIndexInstances(Map<String, Applications> remoteRegionRegistry,
                                         EurekaClientConfig clientConfig,
                                         InstanceRegionChecker instanceRegionChecker) {
        shuffleInstances(clientConfig.shouldFilterOnlyUpInstances(),
                true, remoteRegionRegistry, clientConfig, instanceRegionChecker);
    }

    private void shuffleInstances(boolean filterUpInstances,
                                  boolean indexByRemoteRegions,
                                  @Nullable Map<String, Applications> remoteRegionsRegistry,
                                  @Nullable EurekaClientConfig clientConfig,
                                  @Nullable InstanceRegionChecker instanceRegionChecker) {
        Map<String, VipIndexSupport> secureVirtualHostNameAppMap = new HashMap<>();
        Map<String, VipIndexSupport> virtualHostNameAppMap = new HashMap<>();
        for (Application application : appNameApplicationMap.values()) {
            if (indexByRemoteRegions) {
                application.shuffleAndStoreInstances(remoteRegionsRegistry, clientConfig, instanceRegionChecker);
            } else {
                application.shuffleAndStoreInstances(filterUpInstances);
            }
            this.addInstancesToVIPMaps(application, virtualHostNameAppMap, secureVirtualHostNameAppMap);
        }

        shuffleAndFilterInstances(virtualHostNameAppMap, filterUpInstances);
        shuffleAndFilterInstances(secureVirtualHostNameAppMap, filterUpInstances);

        this.virtualHostNameAppMap.putAll(virtualHostNameAppMap);
        this.virtualHostNameAppMap.keySet().retainAll(virtualHostNameAppMap.keySet());
        this.secureVirtualHostNameAppMap.putAll(secureVirtualHostNameAppMap);
        this.secureVirtualHostNameAppMap.keySet().retainAll(secureVirtualHostNameAppMap.keySet());
    }

    /**
     * Gets the next round-robin index for the given virtual host name.This
     * index is reset after every registry fetch cycle
     * @param virtualHostName the virtual host name.
     * @param secure indicates whether it is a secure request or a non-secure request.
     * @return AtomicLong value representing the next round-robin index.
     */
    public AtomicLong getNextIndex(String virtualHostName, boolean secure){
        Map<String, VipIndexSupport> index = secure ? secureVirtualHostNameAppMap : virtualHostNameAppMap;
        return Optional.ofNullable(index.get(virtualHostName.toUpperCase(Locale.ROOT)))
                .map(VipIndexSupport::getRoundRobinIndex)
                .orElse(null);
    }

    /**
     * Shuffle the instances and filter for only {@link InstanceInfo.InstanceStatus#UP} if required.
     * @param srcMap
     * @param filterUpInstances
     */
    private void shuffleAndFilterInstances(Map<String, VipIndexSupport> srcMap, boolean filterUpInstances) {
        Random shuffleRandom = new Random();
        for (Map.Entry<String, VipIndexSupport> entry : srcMap.entrySet()){
            VipIndexSupport vipIndexSupport = entry.getValue();
            AbstractQueue<InstanceInfo> vipInstances = vipIndexSupport.instances;
            final List<InstanceInfo> filteredInstances;
            if(filterUpInstances){
                filteredInstances = vipInstances.stream().filter(ii->ii.getStatus() == InstanceInfo.InstanceStatus.UP)
                        .collect(Collectors.toList());Collectors.toCollection(()->new ArrayList<>(vipInstances.size()));
            }else{
                filteredInstances = new ArrayList<>(vipInstances);
            }
            Collections.shuffle(filteredInstances, shuffleRandom);
            vipIndexSupport.vipList.set(filteredInstances);
            vipIndexSupport.roundRobinIndex.set(0);
        }
    }

    /**
     * Adds the instances to the internal vip address map.
     * @param app the applications for which the instances need to be added.
     * @param virtualHostNameAppMap
     * @param secureVirtualHostNameAppMap
     */
    private void addInstancesToVIPMaps(Application app, Map<String, VipIndexSupport> virtualHostNameAppMap,
                                       Map<String, VipIndexSupport> secureVirtualHostNameAppMap) {
        //Check and add the instances to the their respective virtual host name mappings
        for (InstanceInfo info : app.getInstances()) {
            String vipAddresses = info.getVIPAddress();
            if (vipAddresses != null) {
                addInstanceToMap(info, vipAddresses, virtualHostNameAppMap);
            }

            String secureVipAddresses = info.getSecureVipAddress();
            if (secureVipAddresses != null) {
                addInstanceToMap(info, secureVipAddresses, secureVirtualHostNameAppMap);
            }
        }
    }

    private void addInstanceToMap(InstanceInfo info, String vipAddresses, Map<String, VipIndexSupport> vipMap) {
        if(vipAddresses != null){
            String[] vipAddressArray = vipAddresses.toUpperCase(Locale.ROOT).split(",");
            for (String vipAddress : vipAddressArray){
                VipIndexSupport vis = vipMap.computeIfAbsent(vipAddress, k->new VipIndexSupport());
                vis.instances.add(info);
            }
        }
    }


    /**
     * Remove the <em>application</em> from the list
     * @param app
     */
    public void removeApplication(Application app){
        this.appNameApplicationMap.remove(app.getName().toUpperCase(Locale.ROOT));
        this.applications.remove(app);
    }


}