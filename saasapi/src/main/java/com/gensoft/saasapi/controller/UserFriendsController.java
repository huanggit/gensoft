package com.gensoft.saasapi.controller;

import com.gensoft.core.annotation.Login;
import com.gensoft.core.pojo.UserInfo;
import com.gensoft.core.web.ApiResult;
import com.gensoft.core.web.BusinessException;
import com.gensoft.dao.userfriends.UserFriend;
import com.gensoft.saasapi.cache.UserInfoCache;
import com.gensoft.saasapi.service.UserFriendService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alan on 16-5-24.
 */
@Component
public class UserFriendsController {

    @Autowired
    private UserFriendService userFriendService;

    @Autowired
    UserInfoCache userInfoCache;

    public List<UserInfo> listMyFriends(@Login UserInfo userInfo) {
        List<UserFriend> userFriends = userFriendService.getUserFriendByUid(userInfo.getId());
        List<UserInfo> userInfos = new ArrayList<>();
        for (UserFriend userFriend : userFriends) {
            UserInfo user = userInfoCache.getUserInfoById(userFriend.getFriendId());
            userInfos.add(user);
        }
        return userInfos;
    }

    public ApiResult addFriend(@Login UserInfo userInfo, @RequestParam("friendId") Long friendId) throws BusinessException {
        Long userId = userInfo.getId();
        if(userFriendService.existsUserFriend(userId, friendId))
            throw new BusinessException(ApiResult.CODE_USER_FRIEND_ALREADY_EXISTS);
        UserFriend userFriend = new UserFriend();
        userFriend.setUserId(userId);
        userFriend.setFriendId(friendId);
        userFriend.setCreateById(userId);
        userFriend.setCreateDate(new Date());
        userFriend.setUpdateById(userId);
        userFriend.setUpdateDate(new Date());
        userFriendService.addUserFriend(userFriend);
        return ApiResult.successInstance();
    }

    public ApiResult deleteFriend(@Login UserInfo userInfo, @RequestParam("friendId") Long friendId) {
        UserFriend userFriend = userFriendService.getUserFriend(userInfo.getId(), friendId);
        if (null != userFriend)
            userFriendService.delUserFriend(userFriend);
        return ApiResult.successInstance();
    }

}
