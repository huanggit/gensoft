package com.gensoft.saasapi.controller;

import com.gensoft.core.annotation.Login;
import com.gensoft.core.pojo.UserInfo;
import com.gensoft.core.web.ApiResult;
import com.gensoft.dao.user.User;
import com.gensoft.dao.usergroups.UserGroup;
import com.gensoft.dao.usergroups.UserGroupMap;
import com.gensoft.dao.usergroups.UserGroupTag;
import com.gensoft.saasapi.pojo.usergroup.UserGroupEntity;
import com.gensoft.saasapi.service.UserGroupService;
import com.gensoft.saasapi.service.UserGroupTagService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alan on 16-5-24.
 */
@Component
public class UserGroupController {

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private UserGroupTagService userGroupTagService;

	public ApiResult listMine(@Login UserInfo userInfo) {
		List<UserGroup> list = userGroupService.getMyGroup(userInfo.getId());
		return ApiResult.successInstance(list);
	}

	public ApiResult listTags() {
		List<UserGroupTag> list = userGroupTagService.getAllUserGroupTag();
		return ApiResult.successInstance(list);
	}

	public ApiResult add(@Login UserInfo userInfo, @RequestBody UserGroupEntity req) {
		UserGroup userGroup = new UserGroup();
		userGroup.setName(req.getName());
		userGroup.setDescipt(req.getDescipt());
		userGroup.setTagId(req.getTagId());
		userGroup.setUpdateById(userInfo.getId());
		userGroup.setCreateDate(new Date());
		userGroup.setUserId(userInfo.getId());
		userGroup.setCreateById(userInfo.getId());
		userGroup.setUpdateDate(new Date());
		userGroupService.addUserGroup(userGroup);
		return ApiResult.successInstance();
	}

	public ApiResult delete(@Login UserInfo userInfo, @RequestParam("groupId") Long groupId) {
		UserGroup userGroup = new UserGroup();
		userGroup.setUserId(userInfo.getId());
		userGroup.setId(groupId);
		userGroupService.delUserGroup(userGroup);
		return ApiResult.successInstance();
	}

	public ApiResult detail(@Login UserInfo userInfo, @RequestParam("groupId") Long groupId) {
		UserGroup userGroup = userGroupService.getUserGroupdetail(groupId);
		return ApiResult.successInstance(userGroup);
	}

	public ApiResult modifyInfo(@Login UserInfo userInfo, @RequestBody UserGroupEntity req) {
		UserGroup userGroup = new UserGroup();
		userGroup.setUserId(userInfo.getId());
		userGroup.setDescipt(req.getDescipt());
		userGroup.setName(req.getName());
		userGroup.setUpdateById(userInfo.getId());
		userGroup.setUpdateDate(new Date());
		userGroupService.updateUserGroup(userGroup);
		return ApiResult.successInstance();
	}

	public ApiResult addUserToGroup(@Login UserInfo userInfo, @RequestParam("groupId") Long groupId, @RequestParam("userId") Long userId) {
		UserGroupMap userGroupMap =new UserGroupMap();
		userGroupMap.setGroupId(groupId);
		userGroupMap.setUserId(userId);
		userGroupMap.setCreateDate(new Date());
		userGroupMap.setUpdateDate(new Date());
		userGroupMap.setUpdateById(userInfo.getId());
		userGroupMap.setCreateById(userInfo.getId());
		userGroupService.addUserToGroup(userGroupMap);
		return ApiResult.successInstance();
	}
	
}
