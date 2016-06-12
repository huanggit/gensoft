package com.gensoft.saasapi.service;

import com.gensoft.dao.user.UserRepository;
import com.gensoft.dao.usergroups.UserGroup;
import com.gensoft.dao.usergroups.UserGroupMapRepository;
import com.gensoft.dao.usergroups.UserGroupRepository;

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

	public void addUserGroup(UserGroup userGroup) {
		userGroupRepository.save(userGroup);
	}

	@Transactional
	public void delUserGroup(UserGroup userGroup) {
		userGroupMapRepository.delUserGroupMapByGid(userGroup.getId());
		userGroupRepository.delete(userGroup);
	}

	public UserGroup getUserGroupdetail(long groupId) {
		return userGroupRepository.findOne(groupId);
	}
	
	public void updateUserGroup(UserGroup userGroup){
		userGroupRepository.save(userGroup);
	}
}
