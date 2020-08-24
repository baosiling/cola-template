package com.baosiling.cola.boot;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * @description:
 * @author: wangzhx
 * @create: 2020-08-23 13:02
 */
public abstract class AbstractRegister implements RegisterI {


    protected <T> void order(List<T> interceptorList) {
        if (interceptorList == null || interceptorList.size() <= 1) {
            return;
        }

        T newInterceptor = interceptorList.get(interceptorList.size() - 1);
        Order order = newInterceptor.getClass().getDeclaredAnnotation(Order.class);
        if (order == null) {
            return;
        }
        int index = interceptorList.size() - 1;
        for (int i = interceptorList.size() - 2; i >= 0; i--) {
            int itemOrderInt = Ordered.LOWEST_PRECEDENCE;
            Order itemOrder = interceptorList.get(i).getClass().getDeclaredAnnotation(Order.class);
            if (itemOrder != null) {
                itemOrderInt = itemOrder.value();
            }
            if (itemOrderInt > order.value()) {
                interceptorList.set(index, interceptorList.get(i));
                index = i;
            } else {
                break;
            }
        }
        if (index < interceptorList.size() - 1) {
            interceptorList.set(index, newInterceptor);
        }
    }
}