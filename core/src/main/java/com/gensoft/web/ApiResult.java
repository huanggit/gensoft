package com.gensoft.web;

/**
 * Created by alan on 16-5-24.
 */
public class ApiResult {

    public static ApiResult successInstance() {
        return new ApiResult(0, "success");
    }

    protected int result;
    protected String note;

    public ApiResult() {
    }

    public void success() {
        this.result = 0;
        this.note = "success";
    }

    public ApiResult(int result, String note) {
        this.result = result;
        this.note = note;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
