package com.gensoft.saasapi.pojo.user;

import com.gensoft.web.ApiResult;

/**
 * Created by alan on 16-5-24.
 */
public class GetVerificationCodeResp extends ApiResult {

    private String verificationCode;

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
