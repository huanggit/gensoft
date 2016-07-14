package com.gensoft.core.web;

import java.io.UnsupportedEncodingException;

/**
 * Created by alan on 16-5-24.
 */
@SuppressWarnings("unchecked")
public class ApiResult<T> {

    public static ApiResult successInstance() {
        return new ApiResult(0);
    }

    public static ApiResult failedInstance(String message) {
        return new ApiResult(1);
    }

    public static ApiResult successInstance(Object result) {
        return new ApiResult(0, result);
    }


    public ApiResult() {
    }


    public ApiResult(int code) {
        this.code = code;
    }

    public ApiResult(int code, T result) {
        this.code = code;
        this.result = result;
    }


    protected int code;
    private T result;
    private String cmd;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
