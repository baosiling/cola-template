package com.netflix.appinfo;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

/**
 * A simple interface for indicating which <em>datacenter</em> a particular instance belongs.
 */
@JsonRootName("dataCenterInfo")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
//TODO
//@JsonTypeIdResolver(DataCenterTypeInfoResolver.class)
public interface DataCenterInfo {
    enum Name {Netflix, Amazon, MyOwn}

    Name getName();
}
