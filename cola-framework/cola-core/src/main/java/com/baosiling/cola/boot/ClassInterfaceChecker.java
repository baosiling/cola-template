package com.baosiling.cola.boot;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Modifier;

public class ClassInterfaceChecker {

    public static boolean check(Class<?> targetClz, String expectedName){
        //If it is interface, just return false
        if(targetClz.isInterface()){
            return false;
        }

        //If it is abstract class, just return false
        if(Modifier.isAbstract(targetClz.getModifiers())){
            return false;
        }

        Class[] interfaces = targetClz.getInterfaces();
        if(ArrayUtils.isEmpty(interfaces)){
            return false;
        }
        for(Class intf : interfaces){
            String intfSimpleName = intf.getSimpleName();
            if(StringUtils.equals(intfSimpleName, expectedName)){
                return true;
            }
        }
        return false;
    }
}
