package com.baosiling.cola.exception;

import com.alibaba.cola.dto.ErrorCodeI;
import com.baosiling.cola.exception.framework.BaseException;
import com.baosiling.cola.exception.framework.BasicErrorCode;

/**
 * @description: BizException is known Exception, no need retry
 * @author: wangzhx
 * @create: 2020-08-21 20:26
 */
public class BizException extends BaseException {
    public BizException(String errMessage) {
        super(errMessage);
        this.setErrCode(BasicErrorCode.BIZ_ERROR);
    }

    public BizException(String errMessage, Throwable e) {
        super(errMessage, e);
        this.setErrCode(BasicErrorCode.BIZ_ERROR);
    }

    public BizException(ErrorCodeI errCode, String errMessage){
        super(errMessage);
        this.setErrCode(errCode);
    }
}