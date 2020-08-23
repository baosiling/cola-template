package com.baosiling.cola.context;

import com.baosiling.cola.exception.BizException;

import java.util.*;
import java.util.function.Supplier;

/**
 * @description: ColaContext - 上下文的统领类，所有上下文信息，均通过此类进出
 * @author: wangzhx
 * @create: 2020-08-22 15:08
 */
public class ColaContext {

    /**
     * 可安全回收上下文，业务上下文需要继承AbstractContext
     */
    private static ThreadLocal<LinkedList<ContextDO>> contextDO = new ThreadLocal<>();

    /**
     * 在携带的上下文中，执行supplier函数，并安全回收本地线程变量
     *
     * @param context
     * @param supplier
     * @param <T>
     * @return
     */
    public static <T> T doWithContext(ContextDO context, Supplier<T> supplier) {
        try {
            set(context);
            return supplier.get();
        } finally {
            remove();
        }
    }

    public static void set(ContextDO context) {
        if (contextDO.get() == null) {
            contextDO.set(new LinkedList<>());
        }
        contextDO.get().add(context);
    }

    public static void remove() {
        if (!exist()) {
            return;
        }
        contextDO.get().removeLast();
    }

    private static boolean exist() {
        return contextDO.get() != null && contextDO.get().size() > 0;
    }

    public static ContextDO get() {
        if (!exist()) {
            throw new BizException("ContextDO is empty");
        }
        return contextDO.get().getLast();
    }


    /**
     * 上下文信息存在2个问题
     * -- 父子线程上下文信息透传
     * -- 线程池中的线程复用不销毁，导致线程上下文信息异常
     * <p>
     * 在父子线程透传时的两种处理方式
     * 1.适用于lambda表达式
     * |-- 首先在子线程内调用setContext -- 为子线程添加上下文信息
     * |-- 最后在子线程调用clearAllContext -- 清空子线程上下文信息，防止线程复用不销毁导致的上下文信息异常
     * <p>
     * 2.适用于线程池创建
     * |-- 线程池中，线程实现由以往的new Runnable改为 new ColaContextRunnable(BizScenario...)来透传
     * <p>
     * 使用示例
     */

    //所有应用需要的Context信息，通过ColaContext的map进行维护，每个context都是一个独立的Threadlocal
    private static Map<Class<? extends ColaContextSupport>, ThreadLocal<ColaContextSupport>> map = new HashMap<>();

    public static <T extends ColaContextSupport> T getContext(Class<T> clazz) {
        if (map.get(clazz) == null) {
            return null;
        }
        return (T) map.get(clazz).get();
    }

    /**
     * 导出所有上下文信息（便于在透传时 设进子线程）
     *
     * @return
     */
    public static Set<ColaContextSupport> getAllContext() {
        Set<ColaContextSupport> set = new HashSet<>();
        map.values().forEach(e -> set.add(e.get()));
        return set;
    }

    /**
     * 设置个别上下文
     * @param context
     */
    public static void setContext(ColaContextSupport... context){
        for(ColaContextSupport colaContext : context){
            if(map.get(colaContext.getClass()) == null){
                synchronized (ColaContext.class){
                    if(map.get(colaContext.getClass()) == null){
                        map.put(colaContext.getClass(), new ThreadLocal<>());
                    }
                }
            }
            map.get(colaContext.getClass()).set(colaContext);
        }
    }

    /**
     * 设置全部上下文（一般结合getAllContext一起使用）
     * @param allContext
     */
    public static void setContext(Set<ColaContextSupport> allContext){
        allContext.forEach(ColaContext::setContext);
    }

    public static void clearContext(Class<? extends ColaContextSupport> clazz){
        if(map.get(clazz) == null){
            return;
        }
        map.get(clazz).remove();
    }

    public static void clearAllContext(){
        map.values().forEach(ThreadLocal::remove);
    }




}