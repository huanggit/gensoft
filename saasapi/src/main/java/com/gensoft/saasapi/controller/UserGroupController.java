package com.gensoft.saasapi.controller;

import com.gensoft.core.annotation.Login;
import com.gensoft.core.pojo.UserInfo;
import com.gensoft.core.web.ApiResult;
import com.gensoft.dao.usergroups.UserGroup;
import com.gensoft.dao.usergroups.UserGroupTag;
import com.gensoft.saasapi.pojo.usergroup.UserGroupEntity;
import com.gensoft.saasapi.service.UserGroupService;
import com.gensoft.saasapi.service.UserGroupTagService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alan on 16-5-24.
 */
@RequestMapping("/group")
@RestController
public class UserGroupController {

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private UserGroupTagService userGroupTagService;

	@RequestMapping(value = "/listMine", method = RequestMethod.GET)
	ApiResult listMine(@Login UserInfo userInfo) {
		List<UserGroup> list = userGroupService.getMyGroup(userInfo.getId());
		return ApiResult.successInstance(list);
	}

	@RequestMapping(value = "/listTags", method = RequestMethod.GET)
	ApiResult listTags() {
		List<UserGroupTag> list = userGroupTagService.getAllUserGroupTag();
		return ApiResult.successInstance(list);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	ApiResult add(@Login UserInfo userInfo, @RequestBody UserGroupEntity req) {
		UserGroup userGroup = new UserGroup();
		userGroup.setUserid(userInfo.getId());
		userGroupService.addUserGroup(userGroup);
		return ApiResult.successInstance();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	ApiResult delete(@Login UserInfo userInfo, @RequestParam String groupId) {
		UserGroup userGroup = new UserGroup();
		userGroup.setUserid(userInfo.getId());
		userGroupService.delUserGroup(userGroup);
		return ApiResult.successInstance();
	}

	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	ApiResult detail(@Login UserInfo userInfo, @RequestParam String groupId) {
		UserGroup userGroup = userGroupService.getUserGroupdetail(new Long(groupId));
		return ApiResult.successInstance(userGroup);
	}

	@RequestMapping(value = "/modifyInfo", method = RequestMethod.POST)
	ApiResult modifyInfo(@Login UserInfo userInfo, @RequestBody UserGroupEntity req) {
		UserGroup userGroup = new UserGroup();
		userGroup.setUserid(userInfo.getId());
		userGroupService.updateUserGroup(userGroup);
		return ApiResult.successInstance();
	}
}
