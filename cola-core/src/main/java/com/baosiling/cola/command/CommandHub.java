package com.baosiling.cola.command;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command Hub holds all the important information about Command
 */
@SuppressWarnings("rawtypes")
@Component
public class CommandHub {

    /**
     * 全局通用的PreInterceptors
     */
    @Setter
    @Getter
    private List<CommandInterceptorI> globalPreInterceptors = new ArrayList<>();

    /**
     * 全局通用的PostInterceptors
     */
    @Setter
    @Getter
    private List<CommandInterceptorI> globalPostInterceptors = new ArrayList<>();

    @Setter
    @Getter
    private Map<Class/*CommandClz*/, CommandInvocation> commandRepository = new HashMap<>();

    /**
     * This Repository is used for return right response type on exception scenarios
     */
    @Getter
    private Map<Class/*CommandClz*/, Class/*ResponseClz*/> responseRepository = new HashMap<>();

    public CommandInvocation getCommandInvocation(Class cmdClass){
        CommandInvocation commandInvocation = commandRepository.get(cmdClass);
        if(commandInvocation == null){
            //TODO throw new
        }
        return commandInvocation;
    }
}
