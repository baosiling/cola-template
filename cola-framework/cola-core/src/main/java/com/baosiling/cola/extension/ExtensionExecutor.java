package com.baosiling.cola.extension;

import com.baosiling.cola.extension.BizScenario;
import com.baosiling.cola.boot.AbstractComponentExecutor;
import com.baosiling.cola.common.ColaConstant;
import com.baosiling.cola.exception.framework.ColaException;
import com.baosiling.cola.logger.Logger;
import com.baosiling.cola.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: ExtensionExecutor
 * @author: wangzhx
 * @create: 2020-08-22 22:21
 */
@Component
public class ExtensionExecutor extends AbstractComponentExecutor {

    private Logger logger = LoggerFactory.getLogger(ExtensionExecutor.class);

    @Autowired
    private ExtensionRepository extensionRepository;

    @Override
    protected <C> C locateComponent(Class<C> targetClz, BizScenario bizScenario) {
        C extension = locateExtension(targetClz, bizScenario);
        logger.debug("[Located Extension]: " + extension.getClass().getSimpleName());
        return null;
    }

    /**
     * if the bizScenarioUniqueIdentity is "ali.tmall.supermarket"
     *
     * the search path is as below:
     * 1.first try to get extension by "ali.tmall.supermarket", if get, return it.
     * 2.loop try to get extension by "ali.tmall.", if get, return it.
     * 3.loop try to get extension by "ali", if get, return it.
     * 4.if not found, try the default extension
     * @param targetClz
     * @param bizScenario
     * @param <Ext>
     * @return
     */
    protected <Ext> Ext locateExtension(Class<Ext> targetClz, BizScenario bizScenario){
        checkNull(bizScenario);

        Ext extension;
        String bizScenarioUniqueIdentity = bizScenario.getUniqueIdentity();
        logger.debug("BizScenario in locateExtension is : " + bizScenarioUniqueIdentity);

        //first try
        extension = firstTry(targetClz, bizScenarioUniqueIdentity);
        if(extension != null){
            return extension;
        }

        //loop try
        extension = loopTry(targetClz, bizScenarioUniqueIdentity);
        if(extension != null){
            return extension;
        }

        throw new ColaException("Can not find extension with ExtensionPoint: " + targetClz + " BizScenario:" + bizScenarioUniqueIdentity);
    }

    private <Ext> Ext firstTry(Class<Ext> targetClz, String bizScenario){
        ExtensionPointI extensionPoint = extensionRepository.getExtensionRepo()
                .get(new ExtensionCoordinate(targetClz.getName(), bizScenario));
        return (Ext) extensionPoint;
    }

    private <Ext> Ext loopTry(Class<Ext> targetClz, String bizScenario){
        Ext extension;
        if(bizScenario == null){
            return null;
        }
        int lastDotIndex = bizScenario.lastIndexOf(ColaConstant.DOT_SEPARATOR);
        while(lastDotIndex != -1){
            bizScenario = bizScenario.substring(0, lastDotIndex);
            extension = (Ext) extensionRepository.getExtensionRepo()
                    .get(new ExtensionCoordinate(targetClz.getName(), bizScenario));
            if(extension != null){
                return extension;
            }
            lastDotIndex = bizScenario.lastIndexOf(ColaConstant.DOT_SEPARATOR);
        }
        return null;
    }

    private void checkNull(BizScenario bizScenario){
        if(bizScenario == null){
            throw new ColaException("BizScenario can bot be null for extension");
        }
    }
}