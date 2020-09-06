package com.netflix.appinfo;

import javax.inject.Singleton;

/**
 * @description:
 *
 * @author: wangzhx
 * @create: 2020-09-06 14:27
 */
//TODO
@Singleton
public class ApplicationInfoManager {

    private static ApplicationInfoManager instance = new ApplicationInfoManager();


    private InstanceInfo instanceInfo;

    public static ApplicationInfoManager getInstance(){
        return instance;
    }

    public InstanceInfo getInfo(){
        return instanceInfo;
    }
}