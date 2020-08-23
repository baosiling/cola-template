package com.baosiling.cola.boot;

import com.alibaba.cola.dto.Command;
import com.baosiling.cola.boot.AbstractRegister;
import com.baosiling.cola.boot.RegisterI;
import com.baosiling.cola.command.CommandHub;
import com.baosiling.cola.command.CommandInterceptorI;
import com.baosiling.cola.command.PostInterceptor;
import com.baosiling.cola.common.ApplicationContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: PostInterceptor
 * @author: wangzhx
 * @create: 2020-08-23 12:44
 */
@Component
public class PostInterceptorRegister extends AbstractRegister {

    @Autowired
    private CommandHub commandHub;

    @Override
    public void doRegistration(Class<?> targetClz) {
        CommandInterceptorI commandInterceptor = (CommandInterceptorI) ApplicationContextHelper.getBean(targetClz);
        PostInterceptor postInterceptorAnn = targetClz.getDeclaredAnnotation(PostInterceptor.class);
        Class<? extends Command>[] supportCommands = postInterceptorAnn.commands();
        registerInterceptor(supportCommands, commandInterceptor);
    }

    private void registerInterceptor(Class<? extends Command>[] supportCommands, CommandInterceptorI commandInterceptor){
        if(supportCommands==null || supportCommands.length == 0){
            commandHub.getGlobalPostInterceptors().add(commandInterceptor);
            order(commandHub.getGlobalPostInterceptors());
        }
    }
}