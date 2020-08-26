package com.baosiling.cola.command;

import com.baosiling.cola.dto.Command;
import com.baosiling.cola.dto.Response;


public interface QueryExecutorI<R extends Response, C extends Command> extends CommandExecutorI<R, C> {

}
