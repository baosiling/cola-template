package com.baosiling.cola.pattern.filter;

import com.baosiling.cola.common.ApplicationContextHelper;

/**
 * 责任链模式工厂
 */
public class FilterChainFactory {

    public static <T> FilterChain<T> buildFilterChain(Class... filterClassList) {
        FilterInvoker last = new FilterInvoker() {
        };
        FilterChain filterChain = new FilterChain();
        for (int i = filterClassList.length - 1; i >= 0; i--){
            FilterInvoker next = last;
            Filter filter = (Filter) ApplicationContextHelper.getBean(filterClassList[i]);
            last = new FilterInvoker<T>() {
                @Override
                public void invoke(T context) {
                    filter.doFilter(context, next);
                }
            };
        }
        filterChain.setHeader(last);
        return filterChain;
    }

}
