package com.baosiling.cola.pattern.filter;

/**
 * 拦截器链
 * @param <T>
 */
public class FilterChain<T> {

    private FilterInvoker<T> header;

    void doFilter(T context) {
        header.invoke(context);
    }

    void setHeader(FilterInvoker<T> header){
        this.header = header;
    }
}
