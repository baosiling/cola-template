package com.netflix.appinfo.providers;

/**
 * This only really exist for legacy support
 */
public interface VipAddressResolver {

    /**
     * Convert <code>VIPAddress</code> by substituting environment variables if necessary.
     * @param vipAddressMacro
     * @return
     */
    String resolveDeploymentContextBasedVipAddress(String vipAddressMacro);
}
