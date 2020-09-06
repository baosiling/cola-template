package com.netflix.appinfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @description:
 * @author: wangzhx
 * @create: 2020-09-06 14:21
 */
//TODO
public class AmazonInfo implements DataCenterInfo, UniqueIdentifier {



    public enum MetaDataKey{
        availabilityZone("availability-zone", "placement/"),;

        protected String name;
        protected String path;

        MetaDataKey(String name, String path) {
            this.name = name;
            this.path = path;
        }

        public String getName(){
            return name;
        }
    }

    private Map<String, String> metadata;

    @Override
    public Name getName() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    public String get(MetaDataKey key){
        return null;
    }

    @JsonProperty("metadata")
    public Map<String, String> getMetadata(){
        return metadata;
    }
}