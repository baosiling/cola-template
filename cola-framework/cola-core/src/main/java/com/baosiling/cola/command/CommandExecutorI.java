package com.baosiling.cola.command;

import com.alibaba.cola.dto.Command;
import com.alibaba.cola.dto.Response;

/**
 * CommandExecutorI
 * @param <R>
 * @param <C>
 */
public interface CommandExecutorI<R extends Response, C extends Command> {

    R execute(C cmd);
}
