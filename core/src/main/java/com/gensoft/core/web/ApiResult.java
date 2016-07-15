package com.gensoft.core.web;

import java.io.UnsupportedEncodingException;

/**
 * Created by alan on 16-5-24.
 */
@SuppressWarnings("unchecked")
public class ApiResult<T> {

    //成功
    public static final int CODE_SUCCESS = 0;
    //程序未处理bug
    public static final int CODE_UNHANDLED_BUG = 1;

    //方法名调用错误
    public static final int CODE_INVALID_CMD = 11;
    //参数调用错误
    public static final int CODE_INVALID_PARAMS = 12;
    //请求报文JSON格式错误
    public static final int CODE_INVALID_JSON_FORMAT_REQUEST = 13;
    //返回结果为null
    public static final int CODE_RETURN_OBJECT_IS_NULL = 14;


    //上传文件类型错误
    public static final int CODE_FILE_INVALID_FORMAT = 110;
    //文件大小超过1M
    public static final int CODE_FILE_SIZE_EXCEEDS_1_M = 111;
    //文件不存在
    public static final int CODE_FILE_DONOT_EXISTS = 112;
    //服务器保存文件错误
    public static final int CODE_FILE_SAVE_ERROR = 113;

    //用户名或密码错误
    //public static final int CODE_USER_PASSWORD_DO_NOT_MATCH = 120;
    //验证码错误
    public static final int CODE_INVALIDE_VERIFICATION_CODE = 121;
    //电话号码已存在
    public static final int CODE_MOBILE_ALREADY_EXISTS = 122;
    //用户名称已存在
    public static final int CODE_USERNAME_ALREADY_EXISTS = 123;
    //好友已存在
    public static final int CODE_USER_FRIEND_ALREADY_EXISTS = 124;


    /* */
    public static ApiResult successInstance() {
        return new ApiResult(null, CODE_SUCCESS);
    }

    public static ApiResult failedInstance(String cmd, int code) {
        return new ApiResult(cmd, code);
    }

    public static ApiResult successInstance(String cmd, Object result) {
        return new ApiResult(cmd, CODE_SUCCESS, result);
    }


    public ApiResult() {
    }

    public ApiResult(String cmd, int code) {
        this.cmd = cmd;
        this.code = code;
    }

    public ApiResult(String cmd, int code, T result) {
        this.cmd = cmd;
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
