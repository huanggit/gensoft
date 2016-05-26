package com.gensoft.saasapi.service;

import com.gensoft.dao.user.UserRepository;
import com.gensoft.dao.usergroups.UserGroupMapRepository;
import com.gensoft.dao.usergroups.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
