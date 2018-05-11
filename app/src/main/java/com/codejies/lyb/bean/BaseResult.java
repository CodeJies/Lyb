package com.codejies.lyb.bean;

/**
 * Created by Jies on 2018/5/10.
 */

public class BaseResult<T> {
    int errorCode;
    String errorMsg;
    T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T datas) {
        this.data = datas;
    }
}
