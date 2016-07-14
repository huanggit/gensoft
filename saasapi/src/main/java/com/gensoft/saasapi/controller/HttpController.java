package com.gensoft.saasapi.controller;

import com.gensoft.core.annotation.AnonymousAccess;
import com.gensoft.core.annotation.Login;
import com.gensoft.core.pojo.UserInfo;
import com.gensoft.core.sms.JavaSmsApi;
import com.gensoft.core.web.ApiResult;
import com.gensoft.dao.user.User;
import com.gensoft.saasapi.pojo.user.ModifyUserInfoReq;
import com.gensoft.saasapi.pojo.user.RegisterReq;
import com.gensoft.saasapi.pojo.user.ResetPasswordReq;
import com.gensoft.saasapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alan on 16-7-12.
 */
@RestController
public class HttpController {

    @Autowired
    UserService userService;

    @Value("${monitor.homeMessage}")
    private String homeMessage;

    @Cacheable("homeMessage")
    @RequestMapping("/")
    String monitorServerRun() {
        return homeMessage;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    ApiResult register(@RequestBody RegisterReq req) {
        User user = new User();
        if(!req.getPassword().equals(req.getRepeatPassword())){
            return ApiResult.successInstance("两次密码不一致");
        }
        user.setUsername(req.getUsername());
        user.setNickname(req.getNickname());
        user.setPassword(req.getPassword());
        user.setMobile(req.getMobile());
        user.setLogo(req.getLogo());
        user.setPlateNo(req.getPlateNo());
        user.setUpdateDate(new Date());
        user.setCreateDate(new Date());
        userService.register(user);
        return ApiResult.successInstance(user);
    }

    @RequestMapping(value = "/existsMobile", method = RequestMethod.POST)
    ApiResult existsMobile(@RequestBody HashMap<String,String> map) {
        String mobile =  map.get("mobile");
        if(userService.getUserByMobile(mobile)){
            return ApiResult.successInstance();
        }else{
            return ApiResult.failedInstance("failed");
        }

    }


    private static final String failedLogin = "用户名密码不正确";


    @RequestMapping(value = "/getVerificationCode", method = RequestMethod.POST)
    ApiResult<String> getVerificationCode(@RequestBody HashMap<String, String> map) {
        // TODO
        String verificationCode = "";
        try {

            String phone =  map.get("mobile");
            int num = (int) (Math.random() * 1000000);
            verificationCode = Integer.toString(num);
            // 设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
            String text = "【南京绅软】您已注册南京绅软智能车载客户端，验证码为" + verificationCode + "。如非本人操作，请忽略本短信";
            //JavaSmsApi.send(text, phone);
            System.out.println(verificationCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResult.successInstance(verificationCode);
    }

    void  setMessage() {
        // TODO
        String verificationCode = "8888";
        try {
            String phone =  "18013955700";
            int num = (int) (Math.random() * 1000000);
            verificationCode = Integer.toString(num);
            // 设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
            String text = "【南京绅软】您已注册南京绅软智能车载客户端，验证码为" + verificationCode + "。如非本人操作，请忽略本短信";
            JavaSmsApi.send(verificationCode, phone);
            System.out.println(verificationCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    ApiResult resetPassword(@Login UserInfo userInfo, @RequestBody ResetPasswordReq req) {
        User user = userService.getUserById(userInfo.getId());
        userService.update(user);
        return ApiResult.successInstance();
    }


}
