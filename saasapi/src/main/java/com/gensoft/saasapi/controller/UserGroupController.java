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

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	ApiResult delete(@Login UserInfo userInfo, @RequestBody HashMap<String,String> map) {
		UserGroup userGroup = new UserGroup();
		userGroup.setUserId(userInfo.getId());
		userGroup.setId(new Long(map.get("groupId")));
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
		userGroup.setUserId(userInfo.getId());
		userGroup.setDescipt(req.getDescipt());
		userGroup.setName(req.getName());
		userGroup.setUpdateById(userInfo.getId());
		userGroup.setUpdateDate(new Date());
		userGroupService.updateUserGroup(userGroup);
		return ApiResult.successInstance();
	}
	
	@RequestMapping(value = "/addUserToGroup", method = RequestMethod.POST)
	ApiResult addUserToGroup(@Login UserInfo userInfo, @RequestBody HashMap<String,String> map) {
		String userIds = map.get("userIds");
		String [] userId = userIds.split(",");
		List<UserGroupMap> userGroupMaps =new ArrayList<UserGroupMap>();
		for(int i =0;i<userId.length;i++){
			UserGroupMap userGroupMap =new UserGroupMap();
			userGroupMap.setGroupId(new Long(map.get("groupId")));
			userGroupMap.setUserId(new Long(userId[i]));
			userGroupMap.setCreateDate(new Date());
			userGroupMap.setUpdateDate(new Date());
			userGroupMap.setUpdateById(userInfo.getId());
			userGroupMap.setCreateById(userInfo.getId());
			userGroupMaps.add(userGroupMap);
		}
		
		userGroupService.addUserToGroup(userGroupMaps);
		return ApiResult.successInstance();
	}
	
}
