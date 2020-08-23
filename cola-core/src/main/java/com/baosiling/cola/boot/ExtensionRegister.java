package com.baosiling.cola.boot;

import com.alibaba.cola.extension.BizScenario;
import com.baosiling.cola.common.ApplicationContextHelper;
import com.baosiling.cola.common.ColaConstant;
import com.baosiling.cola.exception.framework.ColaException;
import com.baosiling.cola.extension.Extension;
import com.baosiling.cola.extension.ExtensionCoordinate;
import com.baosiling.cola.extension.ExtensionPointI;
import com.baosiling.cola.extension.ExtensionRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: ExtensionRegister
 * @author: wangzhx
 * @create: 2020-08-23 21:46
 */
@Component
public class ExtensionRegister implements RegisterI{

    @Autowired
    private ExtensionRepository extensionRepository;

    @Override
    public void doRegistration(Class<?> targetClz) {
        ExtensionPointI extensionPoint = (ExtensionPointI) ApplicationContextHelper.getBean(targetClz);
        Extension extensionAnn = targetClz.getDeclaredAnnotation(Extension.class);
        String extPtClassName = calculateExtensionPoint(targetClz);
        BizScenario bizScenario = BizScenario.valueOf(extensionAnn.bizId(), extensionAnn.useCase(), extensionAnn.scenario());
        ExtensionCoordinate extensionCoordinate = new ExtensionCoordinate(extPtClassName, bizScenario.getUniqueIdentity());
        ExtensionPointI preVal = extensionRepository.getExtensionRepo().put(extensionCoordinate, extensionPoint);
        if(preVal != null){
            throw new ColaException("Duplicate registration is not allowed for :" + extensionCoordinate);
        }
    }

    private String calculateExtensionPoint(Class<?> targetClz){
        Class[] interfaces = targetClz.getInterfaces();
        if(ArrayUtils.isEmpty(interfaces)){
            throw new ColaException("Please assign a extension point interface for " + targetClz);
        }
        for(Class intf : interfaces){
            String extensionPoint = intf.getSimpleName();
            if(StringUtils.contains(extensionPoint, ColaConstant.EXTENSION_EXTPT_NAMING)){
                return intf.getName();
            }
        }
        throw new ColaException("Your name of ExtensionPoint for " + targetClz + " is not valid, must be end of " + ColaConstant.EXTENSION_EXTPT_NAMING);
    }
}