package com.baosiling.cola.common;

import com.baosiling.cola.exception.SysException;
import com.baosiling.cola.exception.framework.BasicErrorCode;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @description: ApplicationContextHelper
 * @author: wangzhx
 * @create: 2020-08-22 14:16
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        T beanInstance = null;
        //优先按type查
        try {
            beanInstance = applicationContext.getBean(clazz);
        } catch (BeansException e) {
        }

        //按name查
        if (beanInstance == null) {
            String simpleName = clazz.getSimpleName();
            //首字母小写
            simpleName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
            beanInstance = (T) applicationContext.getBean(simpleName);
        }

        if (beanInstance == null) {
            new SysException(BasicErrorCode.COLA_ERROR, "Component " + clazz + " can not be found in spring container.");
        }
        return beanInstance;
    }

    public static Object getBean(String clazz){
        return applicationContext.getBean(clazz);
    }


}