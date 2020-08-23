package com.baosiling.cola.logger;

/**
 * @description:
 * @author: wangzhx
 * @create: 2020-08-21 23:54
 */
public class SLFJLogger implements Logger{

    private org.slf4j.Logger slf4jLogger;

    public SLFJLogger(org.slf4j.Logger slf4jLogger){
        this.slf4jLogger = slf4jLogger;
    }

    @Override
    public void debug(String msg) {
        slf4jLogger.debug(msg);
    }

    @Override
    public void info(String msg) {
        slf4jLogger.info(msg);
    }

    @Override
    public void warn(String msg) {
        slf4jLogger.warn(msg);
    }

    @Override
    public void error(String msg) {
        slf4jLogger.error(msg);
    }

    @Override
    public void error(String msg, Throwable t) {
        slf4jLogger.error(msg, t);
    }
}