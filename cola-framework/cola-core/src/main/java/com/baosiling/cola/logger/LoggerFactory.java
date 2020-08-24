package com.baosiling.cola.logger;

/**
 * @description:
 * @author: wangzhx
 * @create: 2020-08-21 23:51
 */
public class LoggerFactory {

    private static boolean useSysLogger = false;

    public static Logger getLogger(Class<?> clazz) {
        if(useSysLogger){
            return SysLogger.getLogger(clazz);
        }
        org.slf4j.Logger slf4jLogger = org.slf4j.LoggerFactory.getLogger(clazz);
        return new SLFJLogger(slf4jLogger);
    }

    public static Logger getLogger(String loggerName) {
        if(useSysLogger){
            return SysLogger.getLogger(loggerName);
        }
        org.slf4j.Logger slf4jLogger = org.slf4j.LoggerFactory.getLogger(loggerName);
        return new SLFJLogger(slf4jLogger);
    }

    public static void activateSysLogger() {
        useSysLogger = true;
    }

    public static void deactivateSysLogger() {
        useSysLogger = false;
    }

}