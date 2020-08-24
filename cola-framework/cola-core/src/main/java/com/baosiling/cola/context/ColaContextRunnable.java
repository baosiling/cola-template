package com.baosiling.cola.context;

import java.util.Set;

/**
 * @description:
 * 能够自动context穿透的Runnable
 * 线程池中加入ColaContextRunnable对象，在构造函数中将context传递到子线程
 * 若不考虑context穿透，可直接用普通的Runnable
 *
 *
 * @author: wangzhx
 * @create: 2020-08-22 15:15
 */
public abstract class ColaContextRunnable implements Runnable {

    private Set<ColaContextSupport> colaContextSupportSet;

    private ColaContextRunnable(){

    }

    public ColaContextRunnable(Set<ColaContextSupport> colaContextSupportSet){
        this.colaContextSupportSet = colaContextSupportSet;
    }

    @Override
    public void run(){
        colaContextSupportSet.forEach(ColaContext::setContext);
        execute();
        ColaContext.clearAllContext();
    }

    public abstract void execute();

}