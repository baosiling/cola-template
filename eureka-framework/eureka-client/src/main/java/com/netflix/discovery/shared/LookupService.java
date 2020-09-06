package com.netflix.discovery.shared;

/**
 * Lookup service for finding active intances.
 */
public interface LookupService<T> {
    Application getApplication(String appName);
}
