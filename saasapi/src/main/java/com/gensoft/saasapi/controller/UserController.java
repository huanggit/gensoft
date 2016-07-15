package com.gensoft.saasapi.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
public class UserController {

	@Autowired
	UserService userService;

    public ApiResult modifyInfo(@Login UserInfo userInfo, @RequestBody ModifyUserInfoReq req) {
		User user = userService.getUserById(userInfo.getId());
		user.setNickname(req.getNickname());
		//Todo - mobile must be unique
		user.setMobile(req.getMobile());
		user.setPlateNo(req.getPlateNo());
		user.setLogo(req.getLogo());
		userService.update(user);
		return ApiResult.successInstance();
	}

    public List<User> findLikeName(@RequestParam("keyword") String keyword) {
		return userService.getUserFindLikeName(keyword);
	}

	public List<UserInfo> listAll() {
		List<User> userList = userService.getUserfindAll();
        List<UserInfo> userInfos = new ArrayList<>();
		if(!CollectionUtils.isEmpty(userList))
			for(User user:userList)
				userInfos.add(new UserInfo(user));
        return userInfos;
	}

	ApiResult resetPassword(@Login UserInfo userInfo, @RequestBody ResetPasswordReq req) {
		//// TODO: 16-7-15
		User user = userService.getUserById(userInfo.getId());
		userService.update(user);
		return ApiResult.successInstance();
	}

}
