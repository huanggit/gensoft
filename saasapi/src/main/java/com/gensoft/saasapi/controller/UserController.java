package com.gensoft.saasapi.controller;

import com.gensoft.core.annotation.AnonymousAccess;
import com.gensoft.core.annotation.Login;
import com.gensoft.core.pojo.UserInfo;
import com.gensoft.dao.user.User;
import com.gensoft.saasapi.pojo.user.ModifyUserInfoReq;
import com.gensoft.saasapi.pojo.user.RegisterReq;
import com.gensoft.saasapi.pojo.user.ResetPasswordReq;
import com.gensoft.saasapi.service.UserService;
import com.gensoft.core.web.ApiResult;
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

    @AnonymousAccess
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    ApiResult register(@RequestBody RegisterReq req) {
        userService.register(req);
        return ApiResult.successInstance();
    }

    private static final String failedLogin = "用户名密码不正确";
    @AnonymousAccess
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ApiResult login(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
        User user = userService.getUserByName(username);
        if (user == null || !password.equals(user.getPassword()))
            return ApiResult.failedInstance(failedLogin);
        //
        UserInfo userInfo = new UserInfo(user.getId(), user.getBindDeviceId());
        HttpSession session = request.getSession(true);
        session.setAttribute("userInfo", userInfo);
        return ApiResult.successInstance();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    ApiResult logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ApiResult.successInstance();
    }

    @AnonymousAccess
    @RequestMapping(value = "/getVerificationCode", method = RequestMethod.POST)
    ApiResult<String> getVerificationCode(@RequestParam long mobile) {
        //TODO
        String verificationCode = "";
        return ApiResult.successInstance(verificationCode);
    }

    @AnonymousAccess
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    ApiResult resetPassword(@RequestBody ResetPasswordReq req) {
        //TODO
        return ApiResult.successInstance();
    }

    @RequestMapping(value = "/modifyInfo", method = RequestMethod.POST)
    ApiResult modifyInfo(@RequestBody ModifyUserInfoReq req) {
        //TODO
        return ApiResult.successInstance();
    }

    @RequestMapping(value = "/findLikeName", method = RequestMethod.POST)
    ApiResult findLikeName(@RequestParam String nameKeyword) {
        //TODO
        return ApiResult.successInstance();
    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    ApiResult findLikeName() {
        //TODO
        return ApiResult.successInstance();
    }

}
