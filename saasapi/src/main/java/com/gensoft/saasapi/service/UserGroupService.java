package com.gensoft.saasapi.service;

import com.gensoft.dao.user.UserRepository;
import com.gensoft.dao.usergroups.UserGroup;
import com.gensoft.dao.usergroups.UserGroupMap;
import com.gensoft.dao.usergroups.UserGroupMapRepository;
import com.gensoft.dao.usergroups.UserGroupRepository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by alan on 16-5-26.
 */
@Service
public class UserGroupService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserGroupRepository userGroupRepository;
	@Autowired
	private UserGroupMapRepository userGroupMapRepository;

	public List<UserGroup> getMyGroup(long userId) {
		return userGroupRepository.getMyGroup(userId);
	}

	@Transactional
	public void addUserGroup(UserGroup userGroup) {
		userGroupRepository.save(userGroup);
		Long userId = userGroup.getUserId();
		UserGroupMap userGroupMap =new UserGroupMap();
		userGroupMap.setGroupId(userGroup.getId());
		userGroupMap.setUserId(userId);
		userGroupMap.setCreateDate(new Date());
		userGroupMap.setUpdateDate(new Date());
		userGroupMap.setUpdateById(userId);
		userGroupMap.setCreateById(userId);
		addUserToGroup(userGroupMap);
	}

	@Transactional
	public void delUserGroup(Long groupId) {
		userGroupMapRepository.delUserGroupMapById(groupId);
		userGroupRepository.delete(groupId);
	}

	public UserGroup getUserGroupdetail(long groupId) {
		return userGroupRepository.findOne(groupId);
	}
	
	public void updateUserGroup(UserGroup userGroup){
		userGroupRepository.save(userGroup);
	}
	
	public void addUserToGroup(UserGroupMap userGroupMap){
		userGroupMapRepository.save(userGroupMap);
	}
	
	public List<Long> getGroupUsers(Long groupId){
		List<Long> receivers = userGroupMapRepository.getGroupUsers(groupId);
		return receivers;
	}

	public boolean existsGroupUser(Long groupId, Long userId){
		return null != userGroupMapRepository.getGroupUsers(groupId);
	}
}
