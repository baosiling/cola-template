package com.baosiling.cola.extension;

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
public class ExtensionExecutor {

    private Logger logger = LoggerFactory.getLogger(ExtensionExecutor.class);

    @Autowired
    private ExtensionRepository extensionRepository;


}