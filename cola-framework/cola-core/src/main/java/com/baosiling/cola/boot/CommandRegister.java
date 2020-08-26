package com.baosiling.cola.boot;

import com.baosiling.cola.dto.Command;
import com.baosiling.cola.command.CommandExecutorI;
import com.baosiling.cola.command.CommandHub;
import com.baosiling.cola.command.CommandInterceptorI;
import com.baosiling.cola.command.CommandInvocation;
import com.baosiling.cola.common.ApplicationContextHelper;
import com.baosiling.cola.common.ColaConstant;
import com.baosiling.cola.exception.framework.ColaException;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @description: CommandRegister
 * @author: wangzhx
 * @create: 2020-08-23 11:50
 */
@Component
public class CommandRegister implements RegisterI {

    @Autowired
    private CommandHub commandHub;

    @Override
    public void doRegistration(Class<?> targetClz) {
        Class<? extends Command> commandClz = getCommandFromExecutor(targetClz);
        CommandInvocation commandInvocation = ApplicationContextHelper.getBean(CommandInvocation.class);
        commandInvocation.setCommandExecutor((CommandExecutorI) ApplicationContextHelper.getBean(targetClz));
        commandInvocation.setPreInterceptors(collectInterceptors(commandClz, true));
        commandInvocation.setPostInterceptors(collectInterceptors(commandClz, false));
        commandHub.getCommandRepository().put(commandClz, commandInvocation);
    }

    private Class<? extends Command> getCommandFromExecutor(Class<?> commandExecutorClz) {
        Method[] methods = commandExecutorClz.getDeclaredMethods();
        for (Method method : methods) {
            if (isExecuteMethod(method)) {
                Class commandClz = checkAndGetCommandParamType(method);
                commandHub.getResponseRepository().put(commandClz, method.getReturnType());
                return commandClz;
            }
        }
        throw new ColaException("There is no " + ColaConstant.EXE_METHOD + "() in " + commandExecutorClz);
    }

    private boolean isExecuteMethod(Method method) {
        return ColaConstant.EXE_METHOD.equals(method.getName()) && !method.isBridge();
    }

    private Class checkAndGetCommandParamType(Method method) {
        Class<?>[] exeParams = method.getParameterTypes();
        if (exeParams.length == 0) {
            throw new ColaException("Execute method in " + method.getDeclaringClass() + " should at least have one parameter");
        }
        if (!Command.class.isAssignableFrom(exeParams[0])) {
            throw new ColaException("Execute method in " + method.getDeclaringClass() + " should be the subClass of Command");
        }
        return exeParams[0];
    }

    private Iterable<CommandInterceptorI> collectInterceptors(Class<? extends Command> commandClz, boolean pre) {
        //add 通用的interceptors
        Iterable<CommandInterceptorI> commandIter = Iterables.concat(pre ? commandHub.getGlobalPreInterceptors() : commandHub.getGlobalPostInterceptors());
        return commandIter;
    }


}