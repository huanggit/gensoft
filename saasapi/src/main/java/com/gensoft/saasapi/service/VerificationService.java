package com.gensoft.saasapi.service;

import com.gensoft.dao.verification.VerificationCode;
import com.gensoft.dao.verification.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by alan on 16-7-15.
 */
@Service
public class VerificationService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    public void saveVerificationCode(Long mobile, String verificationCode, int type){
        VerificationCode code = verificationCodeRepository.getCode(mobile, type);
        if(code == null){
            code = new VerificationCode();
            code.setMobile(mobile);
            code.setCodeType(type);
            code.setCreateDate(new Date());
        }
        code.setUpdateDate(new Date());
        code.setVerificationCode(verificationCode);
        verificationCodeRepository.save(code);
    }

    public boolean invalidVerificationCode(Long mobile, String verificationCode, int type){
        VerificationCode code = verificationCodeRepository.getCode(mobile, type);
        if(code == null)
            return true;
        //TODO time within 5 min
        return !code.getVerificationCode().equals(verificationCode);
    }
}
