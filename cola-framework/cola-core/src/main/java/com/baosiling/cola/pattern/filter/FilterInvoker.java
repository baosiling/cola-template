package com.baosiling.cola.pattern.filter;

public interface FilterInvoker<T> {

    default void invoke(T context){

    };

}
