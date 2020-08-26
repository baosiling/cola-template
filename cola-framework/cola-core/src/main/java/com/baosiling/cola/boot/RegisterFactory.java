package com.baosiling.cola.boot;

import com.baosiling.cola.command.Command;
import com.baosiling.cola.command.PostInterceptor;
import com.baosiling.cola.command.PreInterceptor;
import com.baosiling.cola.event.EventHandler;
import com.baosiling.cola.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RegisterFactory
 */
@Component
public class RegisterFactory {

    @Autowired
    private PreInterceptorRegister preInterceptorRegister;
    @Autowired
    private PostInterceptorRegister postInterceptorRegister;
    @Autowired
    private CommandRegister commandRegister;
    @Autowired
    private ExtensionRegister extensionRegister;
    @Autowired
    private EventRegister eventRegister;

    public RegisterI getRegister(Class<?> targetClz) {
        PreInterceptor preInterceptorAnn = targetClz.getDeclaredAnnotation(PreInterceptor.class);
        if (preInterceptorAnn != null) {
            return preInterceptorRegister;
        }
        PostInterceptor postInterceptorAnn = targetClz.getDeclaredAnnotation(PostInterceptor.class);
        if (postInterceptorAnn != null) {
            return postInterceptorRegister;
        }
        Command commandAnn = targetClz.getDeclaredAnnotation(Command.class);
        if (commandAnn != null) {
            return commandRegister;
        }
        Extension extensionAnn = targetClz.getDeclaredAnnotation(Extension.class);
        if (extensionAnn != null) {
            return extensionRegister;
        }
        EventHandler eventHandlerAnn = targetClz.getDeclaredAnnotation(EventHandler.class);
        if (eventHandlerAnn != null) {
            return eventRegister;
        }
        return null;

    }
}
