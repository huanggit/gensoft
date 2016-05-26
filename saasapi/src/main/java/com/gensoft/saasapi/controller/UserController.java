package com.gensoft.saasapi.controller;

import com.gensoft.core.annotation.AnonymousAccess;
import com.gensoft.core.annotation.Login;
import com.gensoft.core.pojo.UserInfo;
import com.gensoft.core.sms.JavaSmsApi;
import com.gensoft.dao.user.User;
import com.gensoft.saasapi.pojo.user.LoginReq;
import com.gensoft.saasapi.pojo.user.ModifyUserInfoReq;
import com.gensoft.saasapi.pojo.user.RegisterReq;
import com.gensoft.saasapi.pojo.user.ResetPasswordReq;
import com.gensoft.saasapi.service.UserService;

import com.gensoft.core.web.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	ApiResult login(HttpServletRequest request, @RequestBody LoginReq loginReq) {
		User user = userService.getUserByName(loginReq.getUsername());
		if (user == null || !user.getPassword().equals(loginReq.getPassword()))
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
		// TODO
		String verificationCode = "";
		try {
			String phone = mobile + "";
			int num = (int) (Math.random() * 1000000);
			verificationCode = Integer.toString(num);
			// 设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
			String text = "【南京绅软】您已注册南京绅软智能车载客户端，验证码为" + verificationCode + "。如非本人操作，请忽略本短信";
			JavaSmsApi.send(verificationCode, phone);
			System.out.println(verificationCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ApiResult.successInstance(verificationCode);
	}

	@AnonymousAccess
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	ApiResult resetPassword(HttpServletRequest request, @RequestBody ResetPasswordReq req) {
		HttpSession session = request.getSession(true);
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
		User user = userService.getUserById(userInfo.getId());
		userService.update(user);
		return ApiResult.successInstance();
	}

	@RequestMapping(value = "/modifyInfo", method = RequestMethod.POST)
	ApiResult modifyInfo(HttpServletRequest request,@RequestBody ModifyUserInfoReq req) {
		HttpSession session = request.getSession(true);
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
		User user = userService.getUserById(userInfo.getId());
		userService.update(user);
		return ApiResult.successInstance();
	}

	@RequestMapping(value = "/findLikeName", method = RequestMethod.POST)
	ApiResult findLikeName(HttpServletRequest request, @RequestBody HashMap<String, String> map) {
		String namekeyword = map.get("namekeyword");

		List<User> list = userService.getUserFindLikeName(namekeyword);
		return ApiResult.successInstance(list);
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	ApiResult findLikeName() {
		List<User> userList = userService.getUserfindAll();
		return ApiResult.successInstance(userList);
	}

}
