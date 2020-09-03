package com.netflix.appinfo;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Represents the <em>lease</em> information with <em>Eureka</em>.
 *
 * <p>
 * <em>Eureka</em> decide to remove the instance out of its view depending on
 * the duration that is set in {@link EurekaInstanceConfig#getLeaseExpirationDurationInSeconds()} which is
 * held in this lease. The lease also tracks the last time it was renewed.
 * </p>
 */
@JsonRootName("leaseInfo")
public class LeaseInfo {
    public static final int DEFAULT_LEASE_RENEWAL_INTERVAL = 30;
    public static final int DEFAULT_LEASE_DURATION = 90;

    //Client settings
    private int renewalIntervalInSecs = DEFAULT_LEASE_RENEWAL_INTERVAL;
    private int durationInSecs = DEFAULT_LEASE_DURATION;

    //Server populated
    private long registrationTimestamp;
    private long lastRenewalTimestamp;
    private long evictionTimestamp;
    private long serviceUpTimestamp;

    public static final class Builder {
        //TODO
    }
}