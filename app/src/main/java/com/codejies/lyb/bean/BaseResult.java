package com.codejies.lyb.bean;

/**
 * Created by Jies on 2018/5/10.
 */

public class BaseResult<T> {
    int code;
    String msg;
    T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
