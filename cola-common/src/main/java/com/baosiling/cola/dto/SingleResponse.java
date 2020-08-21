package com.baosiling.cola.dto;

public class SingleResponse<T> extends Response {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> SingleResponse<T> of(T data) {
        SingleResponse<T> singleResponse = new SingleResponse<>();
        singleResponse.setData(data);
        singleResponse.setSuccess(true);
        return singleResponse;
    }

    public static SingleResponse buildFailure(String errCode, String errMessage) {
        SingleResponse singleResponse = new SingleResponse();
        singleResponse.setSuccess(false);
        singleResponse.setErrCode(errCode);
        singleResponse.setErrMessage(errMessage);
        return singleResponse;
    }

    public static SingleResponse buildSuccess() {
        SingleResponse singleResponse = new SingleResponse();
        singleResponse.setSuccess(true);
        return singleResponse;
    }
}
