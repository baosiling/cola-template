package com.baosiling.cola.exception;

import com.baosiling.cola.dto.ErrorCodeI;
import com.baosiling.cola.exception.framework.BaseException;
import com.baosiling.cola.exception.framework.BasicErrorCode;

/**
 * @description: System Exception is unexpected Exception, retry might work again.
 * @author: wangzhx
 * @create: 2020-08-21 20:31
 */
public class SysException extends BaseException {
    public SysException(String errMessage) {
        super(errMessage);
        this.setErrCode(BasicErrorCode.SYS_ERROR);
    }

    public SysException(String errMessage, Throwable e) {
        super(errMessage, e);
        this.setErrCode(BasicErrorCode.SYS_ERROR);
    }

    public SysException(ErrorCodeI errCode, String errMessage){
        super(errMessage);
        this.setErrCode(errCode);
    }

    public SysException(String errMessage, ErrorCodeI errCode, Throwable e){
        super(errMessage, e);
        this.setErrCode(errCode);
    }
}