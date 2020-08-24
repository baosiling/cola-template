package com.baosiling.cola.boot;

import com.alibaba.cola.dto.Command;
import com.baosiling.cola.command.CommandHub;
import com.baosiling.cola.command.CommandInterceptorI;
import com.baosiling.cola.command.PreInterceptor;
import com.baosiling.cola.common.ApplicationContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: PreInterceptor
 * @author: wangzhx
 * @create: 2020-08-23 21:31
 */
@Component
public class PreInterceptorRegister extends AbstractRegister {

    @Autowired
    private CommandHub commandHub;

    @Override
    public void doRegistration(Class<?> targetClz) {
        CommandInterceptorI commandInterceptor = (CommandInterceptorI) ApplicationContextHelper.getBean(targetClz);
        PreInterceptor preInterceptorAnn = targetClz.getDeclaredAnnotation(PreInterceptor.class);
        Class<? extends Command>[] supportCommands = preInterceptorAnn.commands();
        registerInterceptor(supportCommands, commandInterceptor);
    }

    private void registerInterceptor(Class<? extends Command>[] supportCommands, CommandInterceptorI commandInterceptor) {
        if (supportCommands == null || supportCommands.length == 0) {
            commandHub.getGlobalPreInterceptors().add(commandInterceptor);
            order(commandHub.getGlobalPreInterceptors());
        }
    }

}