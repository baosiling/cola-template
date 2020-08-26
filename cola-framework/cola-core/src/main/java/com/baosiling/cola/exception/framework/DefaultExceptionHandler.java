package com.baosiling.cola.exception.framework;

import com.baosiling.cola.dto.Command;
import com.baosiling.cola.dto.ErrorCodeI;
import com.baosiling.cola.dto.Response;
import com.baosiling.cola.exception.ExceptionHandlerI;
import com.baosiling.cola.logger.Logger;
import com.baosiling.cola.logger.LoggerFactory;

/**
 * @description: DefaultExceptionHandler
 * @author: wangzhx
 * @create: 2020-08-21 21:07
 */
public class DefaultExceptionHandler implements ExceptionHandlerI {

    private Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    private static DefaultExceptionHandler singleton = new DefaultExceptionHandler();

    @Override
    public void handleException(Command cmd, Response response, Exception e) {
        buildResponse(response, e);
        printLog(cmd, response, e);
    }

    private void printLog(Command cmd, Response response, Exception exception){
        if(exception instanceof BaseException){
            //biz exception is expected, only warn it
            logger.warn(buildErrorMsg(cmd, response));
        }else{
            //sys exception should be monitored, and pay attention to it
            logger.error(buildErrorMsg(cmd, response));
        }
    }

    private String buildErrorMsg(Command cmd, Response response){
        return "Process [" + cmd + "] failed, errorCode: " + response.getErrCode()
                + " errorMsg: " + response.getErrMessage();
    }

    private void buildResponse(Response response, Exception exception){
        if(exception instanceof BaseException){
            ErrorCodeI errCode = ((BaseException) exception).getErrCode();
            response.setErrCode(errCode.getErrCode());
        }else{
            response.setErrCode(BasicErrorCode.SYS_ERROR.getErrCode());
        }
        response.setErrMessage(exception.getMessage());
        response.setSuccess(false);
    }
}