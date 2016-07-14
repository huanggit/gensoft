package com.gensoft.core.web;

/**
 * Created by alan on 16-7-14.
 */
public class BusinessException extends Exception {

    private int code;
    public BusinessException(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
