package com.baosiling.cola.command;

import com.baosiling.cola.dto.Command;
import com.baosiling.cola.dto.Response;

/**
 * CommandExecutorI
 * @param <R>
 * @param <C>
 */
public interface CommandExecutorI<R extends Response, C extends Command> {

    R execute(C cmd);
}
