package com.baosiling.cola.event;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventI;

import java.util.concurrent.ExecutorService;

/**
 * event handler
 */
public interface EventHandlerI<R extends Response, E extends EventI> {

    default ExecutorService getExecutor(){
        return null;
    }

    R execute(E e);

}
