package com.gensoft.saasapi.service;

import com.gensoft.dao.user.UserRepository;
import com.gensoft.dao.userfriends.UserFriend;
import com.gensoft.dao.userfriends.UserFriendRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by alan on 16-5-26.
 */
@Service
public class UserFriendService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserFriendRepository userFriendRepository;
    
    
    public void delUserFriend(UserFriend userFriend){
    	userFriendRepository.delUserFriendByUid(userFriend.getUserId(), userFriend.getFriendId());
    }
    
    public void addUserFriend(UserFriend userFriend){
    	userFriendRepository.save(userFriend);
    }
    public List<UserFriend> getUserFriendByUid(long userId){
    	return userFriendRepository.getUserFriendByUid(userId);
    }
}
