package com.netflix.appinfo;

import com.fasterxml.jackson.annotation.*;
import com.netflix.appinfo.providers.Archaius1VipAddressResolver;
import com.netflix.appinfo.providers.VipAddressResolver;
import com.netflix.discovery.converters.Auto;
import com.netflix.discovery.util.StringCache;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.function.Function;

/**
 * The class that holds information required for registration with
 * <tt>Eureka Server</tt> and to be discovered by other components.
 * <p>
 * <code>@Auto</code> annotated fields are serialized as is; Other fields are
 * serialized as specified by the <code>@Serializer</code>.
 * </p>
 */
//TODO @ProvidedBy(EurekaConfigBasedInstanceInfoProvider.class)
//TODO @Serializer("com.netflix.discovery.converters.EntityBodyConverter")
@XStreamAlias("instance")
@JsonRootName("instance")
public class InstanceInfo {

    private static final String VERSION_UNKNOWN = "unknown";

    /**
     * {@link InstanceInfo} JSON and XML format for port information does not follow the usual conventions,
     * which makes its mapping complicated. This class represents the wire format for port information.
     */
    public static class PortWrapper {
        private final boolean enabled;
        private final int port;

        @JsonCreator
        public PortWrapper(@JsonProperty("@enabled") boolean enabled, @JsonProperty("$") int port) {
            this.enabled = enabled;
            this.port = port;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public int getPort() {
            return port;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(InstanceInfo.class);

    public static final int DEFAULT_PORT = 7001;
    public static final int DEFAULT_SECURE_PORT = 7002;
    public static final int DEFAULT_COUNTRY_ID = 1; //US

    // The (fixed) instanceId for this instanceInfo. This should be unique within the scope of appName.
    private volatile String instanceId;

    private volatile String appName;
    @Auto
    private volatile String appGroupName;

    private volatile String ipAddr;

    private static final String SID_DEFAULT = "na";
    @Deprecated
    private volatile String sid = SID_DEFAULT;

    private volatile int port = DEFAULT_PORT;
    private volatile int securePort = DEFAULT_SECURE_PORT;

    @Auto
    private volatile String homePageUrl;
    @Auto
    private volatile String statusPageUrl;
    @Auto
    private volatile String healthCheckUrl;
    @Auto
    private volatile String secureHealthCheckUrl;
    @Auto
    private volatile String vipAddress;
    @Auto
    private volatile String secureVipAddress;
    @XStreamOmitField
    private String statusPageRelativeUrl;
    @XStreamOmitField
    private String statusPageExplicitUrl;
    @XStreamOmitField
    private String healthCheckRelativeUrl;
    @XStreamOmitField
    private String healthCheckExplicitUrl;
    @XStreamOmitField
    private String healthCheckSecureExplicitUrl;
    @XStreamOmitField
    private String vipAddressUnresolved;
    @XStreamOmitField
    private String secureVipAddressUnresolved;
    @Deprecated
    private volatile int countryId = DEFAULT_COUNTRY_ID; // Defaults to US
    private volatile boolean isSecurePortEnabled = false;
    private volatile boolean isUnsecurePortEnabled = true;
    private volatile DataCenterInfo dataCenterInfo;
    private volatile String hostName;
    private volatile InstanceStatus s;

    public enum InstanceStatus {
        UP, //Ready to receive traffic
        DOWN, //Do not send traffic- health check callback failed
        STARTING, //Just about starting- initializations to be done - do not send traffic
        OUT_OF_SERVICE, //Intentionally shutdown for traffic
        UNKNOWN;

        public static InstanceStatus toEnum(String s) {
            if (s != null) {
                try {
                    return InstanceStatus.valueOf(s.toUpperCase());
                } catch (IllegalArgumentException e) {
                    logger.debug("illegal argument supplied to InstanceStatus.valueOf: {}, defaulting to {}", s, UNKNOWN);
                }
            }
            return UNKNOWN;
        }

    }

    @Override
    public int hashCode() {
        String id = getId();
        return (id == null) ? 31 : (id.hashCode() + 31);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        InstanceInfo other = (InstanceInfo) obj;
        String id = getId();
        if (id == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!id.equals(other.getId())) {
            return false;
        }
        return true;
    }

    public enum PortType {
        SECURE, UNSECURE
    }

    public static final class Builder {
        private static final String COLON = ":";
        private static final String HTTPS_PROTOCOL = "https://";
        private static final String HTTP_PROTOCOL = "https://";
        private final Function<String, String> intern;

        private static final class LazyHolder {
            private static final VipAddressResolver DEFAULT_VIP_ADDRESS_RESOLVER = new Archaius1VipAddressResolver();
        }

        @XStreamOmitField
        private InstanceInfo result;

        @XStreamOmitField
        private final VipAddressResolver vipAddressResolver;

        private String namespace;

        private Builder(InstanceInfo result, VipAddressResolver vipAddressResolver, Function<String, String> intern) {
            this.vipAddressResolver = vipAddressResolver;
            this.result = result;
            this.intern = intern != null ? intern : StringCache::intern;
        }

        public Builder(InstanceInfo instanceInfo) {
            this(instanceInfo, LazyHolder.DEFAULT_VIP_ADDRESS_RESOLVER, null);
        }

        public static Builder newBuilder() {
            return new Builder(new InstanceInfo(), LazyHolder.DEFAULT_VIP_ADDRESS_RESOLVER, null);
        }

        public static Builder newBuilder(Function<String, String> intern) {
            return new Builder(new InstanceInfo(), LazyHolder.DEFAULT_VIP_ADDRESS_RESOLVER, intern);
        }

        public static Builder newBuilder(VipAddressResolver vipAddressResolver) {
            return new Builder(new InstanceInfo(), vipAddressResolver, null);
        }

        public Builder setInstanceId(String instanceId) {
            result.instanceId = instanceId;
            return this;
        }

        /**
         * Set the application name of the instance. This is mostly used in querying of instances.
         *
         * @param appName the application name.
         * @return the instance info builder.
         */
        public Builder setAppName(String appName) {
            result.appName = intern.apply(appName.toUpperCase(Locale.ROOT));
            return this;
        }

        public Builder setAppNameForDeser(String appName) {
            result.appName = appName;
            return this;
        }

        public Builder setAppGroupName(String appGroupName) {
            if (appGroupName != null) {
                result.appGroupName = intern.apply(appGroupName.toUpperCase(Locale.ROOT));
            } else {
                result.appGroupName = null;
            }
            return this;
        }

        public Builder setAppGroupNameForDeser(String appGroupName) {
            result.appGroupName = appGroupName;
            return this;
        }

        //TODO

    }

    @JsonIgnore
    public String getId() {
        if (!StringUtils.isEmpty(instanceId)) {
            return instanceId;
        } else if (dataCenterInfo instanceof UniqueIdentifier) {
            String uniqueId = ((UniqueIdentifier) dataCenterInfo).getId();
            if (!StringUtils.isEmpty(uniqueId)) {
                return uniqueId;
            }
        }
        return hostName;
    }


}
