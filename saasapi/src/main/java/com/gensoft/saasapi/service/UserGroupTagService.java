package com.gensoft.saasapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.gensoft.dao.usergroups.UserGroupTag;
import com.gensoft.dao.usergroups.UserGroupTagRepository;

@Service
public class UserGroupTagService {
	@Autowired
	private UserGroupTagRepository userGroupTagRepository;
	
	public UserGroupTagService() {
		// TODO Auto-generated constructor stub
	}
	
	
	public List<UserGroupTag> getAllUserGroupTag() {
		return (List<UserGroupTag>) userGroupTagRepository.findAll();
	}
}
