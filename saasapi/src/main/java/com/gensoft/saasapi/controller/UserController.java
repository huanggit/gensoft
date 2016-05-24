package com.gensoft.saasapi.controller;

import com.gensoft.saasapi.pojo.user.GetVerificationCodeResp;
import com.gensoft.saasapi.pojo.user.RegisterReq;
import com.gensoft.saasapi.pojo.user.ResetPasswordReq;
import com.gensoft.saasapi.service.UserService;
import com.gensoft.web.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by alan on 16-5-20.
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    ApiResult register(@RequestBody RegisterReq req) {
        userService.register(req);
        return ApiResult.successInstance();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ApiResult login(@RequestParam String username, @RequestParam String password) {
        return ApiResult.successInstance();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "logout";
    }

    @RequestMapping(value = "/getVerificationCode", method = RequestMethod.POST)
    GetVerificationCodeResp getVerificationCode(@RequestParam long mobile) {
        //Todo
        String verificationCode = "";
        GetVerificationCodeResp resp = new GetVerificationCodeResp();
        resp.setVerificationCode(verificationCode);
        resp.success();
        return resp;
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    ApiResult resetPassword(@RequestBody ResetPasswordReq req) {

        return ApiResult.successInstance();
    }
}
