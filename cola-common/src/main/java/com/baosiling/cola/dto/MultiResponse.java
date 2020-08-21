package com.baosiling.cola.dto;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Response with batch record to return,
 * usually use in page query or conditional query
 */
public class MultiResponse<T> extends Response {

    private int total;

    private Collection<T> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Collection<T> getData() {
        return null == data ? new ArrayList<>() : new ArrayList<>(data);
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public static <T> MultiResponse<T> of(Collection<T> data, int total) {
        MultiResponse<T> multiResponse = new MultiResponse<>();
        multiResponse.setSuccess(true);
        multiResponse.setTotal(total);
        multiResponse.setData(data);
        return multiResponse;
    }

    public static <T> MultiResponse<T> ofWithoutTotal(Collection<T> data) {
        return of(data, 0);
    }

    public static MultiResponse buildFailure(String errCode, String errMessage) {
        MultiResponse multiResponse = new MultiResponse();
        multiResponse.setSuccess(false);
        multiResponse.setErrCode(errCode);
        multiResponse.setErrMessage(errMessage);
        return multiResponse;
    }

    public static MultiResponse buildSuccess() {
        MultiResponse multiResponse = new MultiResponse();
        multiResponse.setSuccess(true);
        return multiResponse;
    }


}
