package com.gensoft.saasapi.service;

import com.gensoft.dao.user.UserRepository;
import com.gensoft.dao.userfriends.UserFriendRepository;
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
}
