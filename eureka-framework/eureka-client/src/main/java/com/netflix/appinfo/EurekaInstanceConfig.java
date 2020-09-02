package com.netflix.appinfo;

/**
 * Configuration information required by the instance to register with Eureka server.
 * Once registered, users can look up information from {@link com.netflix.discovery.EurekaClient}
 * based on virtual hostname (also called VIPAddress),
 * the most common way of doing it or by other means to get the information necessary to talk
 * to other instances registered with <em>Eureka</em>
 *
 * <P>
 * As requirements of registration, an id and an appname must be supplied. The id should be unique
 * within the scope of the appname.
 * </P>
 *
 * <P>
 * Note that all configurations are not effective at runtime unless and otherwise specified.
 * </P>
 */
public interface EurekaInstanceConfig {
}
