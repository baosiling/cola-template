package com.baosiling.cola.exception.framework;

/**
 * @description: Infrastructure Exception
 * @author: wangzhx
 * @create: 2020-08-21 20:23
 */
public class ColaException extends BaseException {

    public ColaException(String errMessage) {
        super(errMessage);
    }

    public ColaException(String errMessage, Throwable e) {
        super(errMessage, e);
    }
}