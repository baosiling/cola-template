package com.baosiling.cola.exception.framework;

import com.baosiling.cola.common.ApplicationContextHelper;
import com.baosiling.cola.exception.ExceptionHandlerI;
import org.springframework.context.ApplicationContext;

/**
 * @description: ExceptionHandlerFactory
 * @author: wangzhx
 * @create: 2020-08-22 14:14
 */
public class ExceptionHandlerFactory {

    public static ExceptionHandlerI getExceptionHandler(){

        return ApplicationContextHelper.getBean(ExceptionHandlerI.class);
    }
}