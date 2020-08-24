package com.baosiling.cola.exception;

import com.alibaba.cola.dto.ErrorCodeI;
import com.baosiling.cola.exception.framework.BasicErrorCode;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Assertion utility class that assists in validating arguments.
 *
 * <p>Useful for identifying programmer errors early and clearly at runtime.
 *
 * <p>For example, if the contract of a public method states it does not
 * allow {@code null} arguments, {@code Assert} can be used to validate that contract.
 * <p>
 * For example:
 *
 * <pre class="code>
 *  Assert.notNull(clazz, "The class must not be null");
 *  Assert.isTrue(i > 0, "The value must be greater than zero");</pre>
 * <p>
 * This class is empowered by {@link org.springframework.util.Assert}
 *
 * @author: wangzhx
 * @create: 2020-08-21 20:37
 */
public abstract class Assert {

    /**
     * Assert a boolean expression, throwing {@code BizException}
     *
     * @param expression a boolean expression
     * @param errorCode
     * @param message    the exception message to use if the assertion fails.
     * @throws BizException if exception is {@code false}
     */
    public static void isTrue(boolean expression, ErrorCodeI errorCode, String message) {
        if (!expression) {
            throw new BizException(errorCode, message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        isTrue(expression, BasicErrorCode.BIZ_ERROR, message);
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    public static void notNull(Object object, ErrorCodeI errorCode, String message) {
        if (object == null) {
            throw new BizException(errorCode, message);
        }
    }

    public static void notNull(Object object, String message) {
        notNull(object, BasicErrorCode.BIZ_ERROR, message);
    }

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - the argument " + object + "must not be null");
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(message);
        }
    }

    public static void notEmpty(Collection<?> collection){
        notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    public static void notEmpty(Map<?, ?> map, String message){
        if(CollectionUtils.isEmpty(map)){
            throw new BizException(message);
        }
    }

    public static void notEmpty(Map<?, ?> map){
        notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }


}