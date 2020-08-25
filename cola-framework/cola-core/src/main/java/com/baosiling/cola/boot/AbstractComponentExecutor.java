package com.baosiling.cola.boot;

import com.alibaba.cola.extension.BizScenario;
import com.baosiling.cola.extension.ExtensionCoordinate;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractComponentExecutor {

    /**
     * Execute extension with Response
     * @param targetClz
     * @param bizScenario
     * @param exeFunction
     * @param <R>
     * @param <T>
     * @return
     */
    public <R, T> R execute(Class<T> targetClz, BizScenario bizScenario, Function<T, R> exeFunction){
        T component = locateComponent(targetClz, bizScenario);
        return exeFunction.apply(component);
    }

    public <R, T> R execute(ExtensionCoordinate extensionCoordinate, Function<T, R> exeFunction){
        T component = locateComponent((Class<T>)extensionCoordinate.getExtensionPointClass(), extensionCoordinate.getBizScenario());
        return exeFunction.apply(component);
    }

    public <T> void executeVoid(Class<T> targetClz, BizScenario context, Consumer<T> exeFunction){
        T component = locateComponent(targetClz, context);
        exeFunction.accept(component);
    }

    public <T> void executeVoid(ExtensionCoordinate extensionCoordinate, Consumer<T> exeFunction){
        T component = locateComponent((Class<T>)extensionCoordinate.getExtensionPointClass(), extensionCoordinate.getBizScenario());
        exeFunction.accept(component);
    }

    protected abstract <C> C locateComponent(Class<C> targetClz, BizScenario context);
}
