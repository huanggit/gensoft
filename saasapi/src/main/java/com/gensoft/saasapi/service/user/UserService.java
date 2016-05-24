package com.gensoft.saasapi.service.user;

import com.gensoft.dao.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by alan on 16-5-24.
 */
@Service
public class UserService {

    @Transactional
    public void register(User user) {
        //用户名是否重复
        // bindDeviceId 不为空，去device表查是否存在
    }
}
