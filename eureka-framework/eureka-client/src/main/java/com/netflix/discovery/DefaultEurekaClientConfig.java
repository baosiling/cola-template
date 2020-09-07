package com.netflix.discovery;

import com.google.inject.ProvidedBy;
import com.netflix.discovery.shared.transport.EurekaTransportConfig;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import java.util.List;

/**
 * A default implementation of eureka client configuration as required by
 * {@link EurekaClientConfig}
 *
 * <p>
 * The information required for configuring eureka client is provided in a
 * configuration file. The configuration file is searched for in the classpath
 * with the name specified by the property <em>eureka.client.props</em> and with
 * the suffix <em>.properties</em>. If the property is not specified,
 * <em>eureka-client.properties</em> is assumed as the default. The properties
 * that are looked up uses the <em>namespace</em> passed on to this class.
 * </p>
 *
 * <p>
 *  If the <em>eureka.environment</em> property is specified, additionally
 *  <em>eureka-client-<eureka.environment>.properties</em> is loaded in addition to
 *  <em>eureka-client.properties</em>
 * </p>
 */
@Singleton
//TODO @ProvidedBy(DefaultEurekaClientConfigProvider.class)
public class DefaultEurekaClientConfig implements EurekaClientConfig{
    @Override
    public int getRegistryFetchIntervalSeconds() {
        return 0;
    }

    @Override
    public int getInstanceInfoReplicationIntervalSeconds() {
        return 0;
    }

    @Override
    public int getInitialInstanceInfoReplicationIntervalSeconds() {
        return 0;
    }

    @Override
    public int getEurekaServiceUrlPollIntervalSeconds() {
        return 0;
    }

    @Override
    public String getProxyHost() {
        return null;
    }

    @Override
    public String getProxyPort() {
        return null;
    }

    @Override
    public String getProxyUserName() {
        return null;
    }

    @Override
    public String getProxyPassword() {
        return null;
    }

    @Override
    public boolean shouldGzipContent() {
        return false;
    }

    @Override
    public int getEurekaServerReadTimeoutSeconds() {
        return 0;
    }

    @Override
    public int getEurekaServerConnectTimeoutSeconds() {
        return 0;
    }

    @Override
    public String getBackupRegistryImpl() {
        return null;
    }

    @Override
    public int getEurekaServerTotalConnections() {
        return 0;
    }

    @Override
    public int getEurekaServerTotalConnectionsPerHost() {
        return 0;
    }

    @Override
    public String getEurekaServerURLContext() {
        return null;
    }

    @Override
    public String getEurekaServerPort() {
        return null;
    }

    @Override
    public String getEurekaServerDNSName() {
        return null;
    }

    @Override
    public boolean shouldUseDnsForFetchingServiceUrls() {
        return false;
    }

    @Override
    public boolean shouldRegisterWithEureka() {
        return false;
    }

    @Override
    public boolean shouldPreferSameZoneEureka() {
        return false;
    }

    @Override
    public boolean allowRedirects() {
        return false;
    }

    @Override
    public boolean shouldLogDeltaDiff() {
        return false;
    }

    @Override
    public boolean shouldDisableDelta() {
        return false;
    }

    @Nullable
    @Override
    public String fetchRegistryForRemoteRegions() {
        return null;
    }

    @Override
    public String getRegion() {
        return null;
    }

    @Override
    public String[] getAvailabilityZones(String region) {
        return new String[0];
    }

    @Override
    public List<String> getEurekaServerServiceUrls(String myZone) {
        return null;
    }

    @Override
    public boolean shouldFilterOnlyUpInstances() {
        return false;
    }

    @Override
    public int getEurekaConnectionIdleTimeoutSeconds() {
        return 0;
    }

    @Override
    public boolean shouldFetchRegistry() {
        return false;
    }

    @Nullable
    @Override
    public String getRegistryRefreshSingleVipAddress() {
        return null;
    }

    @Override
    public int getHeartbeatExecutorThreadPoolSize() {
        return 0;
    }

    @Override
    public int getHeartbeatExecutorExponentialBackOffBound() {
        return 0;
    }

    @Override
    public int getCacheRefreshExecutorThreadPoolSize() {
        return 0;
    }

    @Override
    public int getCacheRefreshExecutorExponentialBackOffBound() {
        return 0;
    }

    @Override
    public String getDollarReplacement() {
        return null;
    }

    @Override
    public String getEscapeCharReplacement() {
        return null;
    }

    @Override
    public boolean shouldOnDemandUpdateStatusChange() {
        return false;
    }

    @Override
    public String getEncoderName() {
        return null;
    }

    @Override
    public String getDecoderName() {
        return null;
    }

    @Override
    public String getClientDataAccept() {
        return null;
    }

    @Override
    public String getExperimental(String name) {
        return null;
    }

    @Override
    public EurekaTransportConfig getTransportConfig() {
        return null;
    }
}
