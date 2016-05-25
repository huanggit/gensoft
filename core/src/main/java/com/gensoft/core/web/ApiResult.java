package com.gensoft.core.web;

/**
 * Created by alan on 16-5-24.
 */
public class ApiResult<T> {

    public static ApiResult successInstance() {
        return new ApiResult(0, "success");
    }

    public static ApiResult failedInstance(String message) {
        return new ApiResult(1, message);
    }

    public static ApiResult successInstance(Object result) {
        return new ApiResult(0, "success", result);
    }

    @Override
    public String toString(){
        return "{code:"+code+",note:\""+note+"\"}";
    }

    protected int code;
    protected String note;
    private T result;

    public ApiResult() {
    }


    public ApiResult(int code, String note) {
        this.code = code;
        this.note = note;
    }

    public ApiResult(int code, String note, T result) {
        this.code = code;
        this.note = note;
        this.result = result;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
