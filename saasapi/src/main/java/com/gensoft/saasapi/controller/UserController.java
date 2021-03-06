package com.gensoft.saasapi.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gensoft.core.annotation.WebSocketController;
import com.gensoft.core.web.BusinessException;
import com.gensoft.saasapi.cache.UserInfoCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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

/**
 * Created by alan on 16-5-20.
 */
@Component
@WebSocketController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserInfoCache userInfoCache;

    public User getUserInfo(@Login UserInfo userInfo) throws BusinessException {
        User user = userService.getUserById(userInfo.getId());
        return user;
    }

    public ApiResult modifyUserInfo(@Login UserInfo userInfo, @RequestBody ModifyUserInfoReq req) throws BusinessException {
        Long mobile = req.getMobile();
        if (userService.existsUserMobile(mobile))
            throw new BusinessException(ApiResult.CODE_MOBILE_ALREADY_EXISTS);
        User user = userService.getUserById(userInfo.getId());
        user.setNickname(req.getNickname());
        user.setMobile(mobile);
        user.setPlateNo(req.getPlateNo());
        user.setLogo(req.getLogo());
        userService.update(user);
        userInfoCache.refreshUserMap();
        return ApiResult.successInstance();
    }

    public List<UserInfo> findUsersLikeName(@RequestParam("keyword") String keyword) {
        List<User> userList = userService.getUserFindLikeName(keyword);
        List<UserInfo> userInfos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userList))
            for (User user : userList)
                userInfos.add(new UserInfo(user));
        return userInfos;
    }

    public List<UserInfo> listAllUsers() {
        List<User> userList = userService.getUserfindAll();
        List<UserInfo> userInfos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userList))
            for (User user : userList)
                userInfos.add(new UserInfo(user));
        return userInfos;
    }

    ApiResult resetUserPassword(@Login UserInfo userInfo, @RequestBody ResetPasswordReq req) {
        //// TODO: 16-7-15
        User user = userService.getUserById(userInfo.getId());
        //userService.update(user);
        return ApiResult.successInstance();
    }

}
