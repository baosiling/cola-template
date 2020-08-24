package com.baosiling.cola.command;

import com.alibaba.cola.dto.Command;
import com.alibaba.cola.dto.Response;
import com.baosiling.cola.exception.framework.ExceptionHandlerFactory;
import com.baosiling.cola.logger.Logger;
import com.baosiling.cola.logger.LoggerFactory;
import com.google.common.collect.FluentIterable;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CommandInvocation {

    private static Logger logger = LoggerFactory.getLogger(CommandInvocation.class);

    @Setter
    private CommandExecutorI commandExecutor;
    @Setter
    private Iterable<CommandInterceptorI> preInterceptors;
    @Setter
    private Iterable<CommandInterceptorI> postInterceptors;

    @Autowired
    private CommandHub commandHub;

    public CommandInvocation(){

    }

    public CommandInvocation(CommandExecutorI commandExecutor, List<CommandInterceptorI> preInterceptors,
                             List<CommandInterceptorI> postInterceptors){
        this.commandExecutor = commandExecutor;
        this.preInterceptors = preInterceptors;
        this.postInterceptors = postInterceptors;
    }

    public Response invoke(Command command){
        Response response = null;
        try {
            preIntercept(command);
            response = commandExecutor.execute(command);
        } catch (Exception e) {
            response = getResponseInstance(command);
            response.setSuccess(false);
            ExceptionHandlerFactory.getExceptionHandler().handleException(command, response, e);
        } finally {
            //make sure post interceptors preforms even though exception happens.
            postIntercept(command, response);
        }
        return response;
    }

    private void preIntercept(Command command){
        for(CommandInterceptorI preInterceptor : FluentIterable.from(preInterceptors).toList()){
            preInterceptor.preIntercept(command);
        }
    }

    private void postIntercept(Command command, Response response){
        for(CommandInterceptorI postInterceptor : FluentIterable.from(postInterceptors).toList()){
            postInterceptor.postIntercept(command, response);
        }
    }

    private Response getResponseInstance(Command cmd){
        Class responseClazz = commandHub.getResponseRepository().get(cmd.getClass());
        try {
            return (Response) responseClazz.newInstance();
        } catch (Exception e) {
            return new Response();
        }
    }


}
