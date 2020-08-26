package com.baosiling.cola.pattern.filter;

public interface Filter<T> {
    void doFilter(T context, FilterInvoker<T> nextFilter);
}
