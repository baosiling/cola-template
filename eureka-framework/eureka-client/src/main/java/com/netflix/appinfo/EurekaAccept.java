package com.netflix.appinfo;

import java.util.HashMap;
import java.util.Map;

public enum EurekaAccept {
    full, compact;
    public static final String HTTP_X_EUREKA_ACCEPT = "X-Eureka-Accept";

    private static final Map<String, EurekaAccept> decoderNameToAcceptMap = new HashMap<>();

//TODO    static {
//        decoderNameToAcceptMap.put(CodecWrapper)
//    }
}
