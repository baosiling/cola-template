package com.baosiling.cola.command;

import com.baosiling.cola.dto.Command;
import com.baosiling.cola.dto.Response;

/**
 * Interceptor will do AOP processing before or after Command Execution
 */
public interface CommandInterceptorI {

    default public void preIntercept(Command command){

    }

    default public void postIntercept(Command command, Response response){

    }
}
