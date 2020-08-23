package com.baosiling.cola.exception;

import com.alibaba.cola.dto.Command;
import com.alibaba.cola.dto.Response;

/**
 * ExceptionHandlerI provide a backdoor that Application can override the default Exception handling
 */
public interface ExceptionHandlerI {
    void handleException(Command cmd, Response response, Exception e);
}
