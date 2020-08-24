package com.baosiling.cola.command;

import com.alibaba.cola.dto.Command;
import com.alibaba.cola.dto.Response;

/**
 * CommandBus
 */
public interface CommandBusI {

    /**
     * Send command to CommandBus, then the command will be executed by CommandExecutor
     * @param cmd Command or Query
     * @return Response
     */
    Response send(Command cmd);
}
