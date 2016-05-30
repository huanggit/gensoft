package com.gensoft.saasapi.controller;

import com.gensoft.core.annotation.Login;
import com.gensoft.core.pojo.UserInfo;
import com.gensoft.core.web.ApiResult;
import com.gensoft.dao.userfriends.UserFriend;
import com.gensoft.saasapi.service.UserFriendService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alan on 16-5-24.
 */
@RequestMapping("/friends")
@RestController
public class UserFriendsController {

    @Autowired
    private UserFriendService userFriendService;

    @RequestMapping(value = "/listMine", method = RequestMethod.GET)
    ApiResult listMine(@Login UserInfo userInfo) {
    	List<UserFriend> list = userFriendService.getUserFriendByUid(userInfo.getId());
        return ApiResult.successInstance(list);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ApiResult add(@Login UserInfo userInfo, @RequestParam String friendId) {
    	UserFriend userFriend = new UserFriend();
    	userFriend.setUserId(userInfo.getId());
    	userFriend.setFriendId(new Long(friendId));
    	userFriendService.addUserFriend(userFriend);
        return ApiResult.successInstance();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    ApiResult delete(@Login UserInfo userInfo, @RequestParam String friendId) {
    	UserFriend userFriend = new UserFriend();
    	userFriend.setUserId(userInfo.getId());
    	userFriend.setFriendId(new Long(friendId));
    	userFriendService.delUserFriend(userFriend);
        return ApiResult.successInstance();
    }

}
