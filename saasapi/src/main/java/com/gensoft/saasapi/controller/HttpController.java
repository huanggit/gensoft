package com.gensoft.saasapi.controller;

import com.gensoft.core.annotation.Login;
import com.gensoft.core.pojo.UserInfo;
import com.gensoft.core.sms.JavaSmsApi;
import com.gensoft.core.web.ApiResult;
import com.gensoft.core.web.BusinessException;
import com.gensoft.dao.user.User;
import com.gensoft.dao.verification.VerificationCode;
import com.gensoft.saasapi.cache.UserInfoCache;
import com.gensoft.saasapi.pojo.user.RegisterReq;
import com.gensoft.saasapi.pojo.user.ResetPasswordReq;
import com.gensoft.saasapi.service.UserService;
import com.gensoft.saasapi.service.VerificationService;
import com.gensoft.saasapi.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by alan on 16-7-12.
 */
@RestController
public class HttpController {

    @Autowired
    UserService userService;

    @Autowired
    VerificationService verificationService;


    @Value("${monitor.homeMessage}")
    private String homeMessage;

    @Cacheable("homeMessage")
    @RequestMapping("/")
    String monitorServerRun() {
        return homeMessage;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    ApiResult register(@RequestBody RegisterReq req, @RequestParam(value = "file", required = false) MultipartFile file) throws BusinessException {
        return userService.register(req, file);
    }

    @RequestMapping(value = "/registerNoLogo", method = RequestMethod.POST)
    ApiResult registerNoLogo(@RequestBody RegisterReq req) throws BusinessException {
        return userService.register(req, null);
    }

    @RequestMapping(value = "/existsMobile", method = RequestMethod.POST)
    ApiResult existsMobile(@RequestBody Map<String, Object> map) {
        Long mobile = (Long) map.get("mobile");
        Boolean exists = userService.existsUserMobile(mobile);
        return ApiResult.successInstance("existsMobile", exists);
    }

    @RequestMapping(value = "/getVerificationCode", method = RequestMethod.POST)
    ApiResult<String> getVerificationCode(@RequestBody Map<String, Object> map) {
        Random rand = new Random();
        Integer result = 100000 + rand.nextInt(900000);
        String verificationCode = result.toString();
        // 设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
        String text = "【南京绅软】您已注册南京绅软智能车载客户端，验证码为" + verificationCode + "。如非本人操作，请忽略本短信";
        Long mobile = (Long) map.get("mobile");
        verificationService.saveVerificationCode(mobile,verificationCode,1);
        //JavaSmsApi.send(text, mobile.toString());
        return ApiResult.successInstance("getVerificationCode", verificationCode);
    }

}
