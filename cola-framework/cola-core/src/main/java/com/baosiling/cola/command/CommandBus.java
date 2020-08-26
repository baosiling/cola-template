package com.baosiling.cola.command;

import com.baosiling.cola.dto.Command;
import com.baosiling.cola.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Just send Command to CommandBus.
 */

@Component
public class CommandBus implements CommandBusI{

    @Autowired
    private CommandHub commandHub;

    @Override
    public Response send(Command cmd) {
        return commandHub.getCommandInvocation(cmd.getClass()).invoke(cmd);
    }
}
