package com.netflix.appinfo;

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
    }

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
}