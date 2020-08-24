package com.baosiling.cola.exception.framework;

import com.alibaba.cola.dto.ErrorCodeI;

/**
 * Base Exception is the parent of all exceptions
 */
public abstract class BaseException extends RuntimeException {

    private ErrorCodeI errCode;

    public BaseException(String errMessage){
        super(errMessage);
    }

    public BaseException(String errMessage, Throwable e){
        super(errMessage, e);
    }

    public ErrorCodeI getErrCode() {
        return errCode;
    }

    public void setErrCode(ErrorCodeI errCode) {
        this.errCode = errCode;
    }
}
