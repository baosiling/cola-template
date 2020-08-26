package com.baosiling.cola.boot;

import com.baosiling.cola.exception.framework.ColaException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 应用的核心引导启动类
 * 负责扫描在applicationContext.xml中配置的packages.获取CommandExecutors,interceptors,extensions,validators等
 * 交给各个注册器进行注册
 *
 */
public class Bootstrap {

    @Setter
    @Getter
    private List<String> packages;
    private ClassPathScanHandler classPathScanHandler;

    @Autowired
    private RegisterFactory registerFactory;

    public void init(){
        Set<Class<?>> classSet =  scanConfiguredPackages();
        registerBeans(classSet);
    }

    private void registerBeans(Set<Class<?>> classes){
        for(Class<?> targetClz : classes){
            RegisterI register = registerFactory.getRegister(targetClz);
            if(register != null){
                register.doRegistration(targetClz);
            }
        }
    }

    private Set<Class<?>> scanConfiguredPackages(){
        if(CollectionUtils.isEmpty(packages)){
            throw new ColaException("Command packages is not specified.");
        }
        String[] pkgs = new String[packages.size()];
        classPathScanHandler = new ClassPathScanHandler(packages.toArray(pkgs));

        Set<Class<?>> classSet = new TreeSet<>(new ClassNameComparator());
        for(String packageName : packages){
            classSet.addAll(classPathScanHandler.getPackageAllClasses(packageName, true));
        }
        return classSet;
    }
}
