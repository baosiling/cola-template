package com.baosiling.cola.boot;

import com.alibaba.cola.event.EventI;
import com.baosiling.cola.common.ApplicationContextHelper;
import com.baosiling.cola.common.ColaConstant;
import com.baosiling.cola.event.EventHandlerI;
import com.baosiling.cola.event.EventHub;
import com.baosiling.cola.exception.framework.ColaException;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class EventRegister implements RegisterI {

    @Autowired
    private EventHub eventHub;

    @Override
    public void doRegistration(Class<?> targetClz) {
        EventHandlerI eventHandler = (EventHandlerI) ApplicationContextHelper.getBean(targetClz);
        eventHub.register(getEventFromExecutor(targetClz), eventHandler);
    }

    private Class<? extends EventI> getEventFromExecutor(Class<?> eventExecutorClz) {
        Method[] methods = eventExecutorClz.getMethods();
        for(Method method : methods){
            if(isExecuteMethod(method)){
                return checkAndGetEventParamType(method);
            }
        }
        throw new ColaException("Event param in " + eventExecutorClz + " " + ColaConstant.EXE_METHOD + "() is not detected");
    }

    private boolean isExecuteMethod(Method method){
        return ColaConstant.EXE_METHOD.equals(method.getName()) && !method.isBridge();
    }

    private Class checkAndGetEventParamType(Method method){
        Class<?>[] parameterTypes = method.getParameterTypes();
        if(ArrayUtils.isEmpty(parameterTypes)){
            throw new ColaException("Execute method in " + method.getDeclaringClass() + " should at least have one parameter");
        }
        if(!EventI.class.isAssignableFrom(parameterTypes[0])){
            throw new ColaException("Execute method in " + method.getDeclaringClass() + " should be the subClass of Event");
        }
        return parameterTypes[0];
    }
}
