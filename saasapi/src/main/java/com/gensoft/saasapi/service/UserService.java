package com.gensoft.saasapi.service;

import com.gensoft.dao.user.User;
import com.gensoft.dao.user.UserRepository;
import com.gensoft.saasapi.pojo.user.RegisterReq;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by alan on 16-5-24.
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void register(RegisterReq user) {
		// 用户名是否重复
		// bindDeviceId 不为空，去device表查是否存在
	}

	public User getUserByName(String username) {
		return userRepository.findByUsername(username);
	}

	public List<User> getUserfindAll() {
		List<User> userList = (List<User>) userRepository.findAll();
		return userList;
	}
	public List<User> getUserFindLikeName(String likeName) {
		List<User> userList = (List<User>) userRepository.getUserFindLikeName("%"+likeName+"%");
		return userList;
	}
	
	public User getUserById(Long id){
		return userRepository.findOne(id);
	}
	
	public void update(User user){
		userRepository.save(user);
	}
}
