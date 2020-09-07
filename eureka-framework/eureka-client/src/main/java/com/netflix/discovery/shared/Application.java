package com.netflix.discovery.shared;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.InstanceRegionChecker;
import com.netflix.discovery.provider.Serializer;
import com.netflix.discovery.util.StringCache;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @description: The application class holds the list of instances for a particular application.
 * @author: wangzhx
 * @create: 2020-09-06 14:36
 */
@Serializer("com.netflix.discovery.converters.EntityBodyConverter")
@XStreamAlias("application")
@JsonRootName("application")
public class Application {

    private static Random shuffleRandom = new Random();

    private String name;

    @XStreamOmitField
    private volatile boolean isDirty = false;

    @XStreamImplicit
    private final Set<InstanceInfo> instances;

    private final AtomicReference<List<InstanceInfo>> shuffledInstances;

    private final Map<String, InstanceInfo> instancesMap;

    public Application() {
        instances = new LinkedHashSet<>();
        instancesMap = new ConcurrentHashMap<>();
        shuffledInstances = new AtomicReference<>();
    }

    public Application(String name) {
        this();
        this.name = StringCache.intern(name);
    }

    @JsonCreator
    public Application(@JsonProperty("name") String name, @JsonProperty("instance") List<InstanceInfo> instances) {
        this(name);
        for (InstanceInfo instanceInfo : instances) {
            addInstance(instanceInfo);
        }
    }

    /**
     * Add the given instance info the list.
     *
     * @param i
     */
    public void addInstance(InstanceInfo i) {
        instancesMap.put(i.getId(), i);
        synchronized (instances) {
            instances.remove(i);
            instances.add(i);
            isDirty = true;
        }
    }

    /**
     * remove the given instance info the list
     *
     * @param i
     */
    public void removeInstance(InstanceInfo i) {
        removeInstance(i, true);
    }

    private void removeInstance(InstanceInfo i, boolean markAsDirty) {
        instancesMap.remove(i.getId());
        synchronized (instances) {
            instances.remove(i);
            if (markAsDirty) {
                isDirty = true;
            }
        }
    }

    /**
     * Gets the list of instances associated with this particular application.
     * <p>
     * Note that the instances are always returned with random order after
     * shuffling to avoid traffic to the same instances during startup. The
     * shuffling always happens once after every fetch cycle as specified in
     * {@link EurekaClientConfig#getRegistryFetchIntervalSeconds}.
     * </p>
     *
     * @return the list of shuffled instances associated with this application.
     */
    public List<InstanceInfo> getInstances() {
        return Optional.ofNullable(shuffledInstances.get()).orElseGet(this::getInstanceAsIsFromEureka);
    }

    /**
     * Gets the list of non-shuffled and non-filtered instances associated with this particular application.
     *
     * @return list of non-shuffled and non-filtered instances associated with this particular application.
     */
    @JsonIgnore
    public List<InstanceInfo> getInstanceAsIsFromEureka() {
        synchronized (instances) {
            return new ArrayList<>(this.instances);
        }
    }

    /**
     * Get the instance info that matches the given id.
     *
     * @param id the id for which the instance info needs to be returned.
     * @return the instance info object.
     */
    public InstanceInfo getByInstanceId(String id) {
        return instancesMap.get(id);
    }

    /**
     * Gets the name of the application.
     *
     * @return the name of the application.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = StringCache.intern(name);
    }

    /**
     * @return the number of instances in this application
     */
    public int size() {
        return instances.size();
    }

    private void _shuffleAndStoreInstances(boolean filterUpInstances, boolean indexByRemoteRegions,
                                           @Nullable Map<String, Applications> remoteRegionRegistry,
                                           @Nullable EurekaClientConfig clientConfig,
                                           @Nullable InstanceRegionChecker instanceRegionChecker) {
        List<InstanceInfo> instanceInfoList;
        synchronized (instances) {
            instanceInfoList = new ArrayList<>(instances);
        }
        boolean remoteIndexingActive = indexByRemoteRegions && null != instanceRegionChecker && null != clientConfig
                && null != remoteRegionRegistry;
        if (remoteIndexingActive || filterUpInstances) {
            Iterator<InstanceInfo> it = instanceInfoList.iterator();
            while (it.hasNext()) {
                InstanceInfo instanceInfo = it.next();
                if (filterUpInstances && InstanceInfo.InstanceStatus.UP != instanceInfo.getStatus()) {
                    it.remove();
                } else if (remoteIndexingActive) {
                    String instanceRegion = instanceRegionChecker.getInstanceRegion(instanceInfo);
                    if (!instanceRegionChecker.isLocalRegion(instanceRegion)) {
                        //TODO Applications
                    }
                }
            }
        }
    }


}