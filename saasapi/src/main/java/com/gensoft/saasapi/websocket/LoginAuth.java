package com.gensoft.saasapi.websocket;

import com.gensoft.core.pojo.UserInfo;
import com.gensoft.core.web.ApiResult;
import com.gensoft.dao.user.User;
import com.gensoft.saasapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by alan on 16-7-12.
 */
@Service
public class LoginAuth {

    @Autowired
    UserService userService;

    public UserInfo authUser(String encryptedCode){
        NameAndPasswd nameAndPasswd = decode(encryptedCode);
        return validateUser(nameAndPasswd);
    }

    private NameAndPasswd decode(String encryptedCode){
        //TODO
        String[] tmp = encryptedCode.split("\\+");
        NameAndPasswd result = new NameAndPasswd(tmp[0],tmp[1]);
        return result;
    }

    private UserInfo validateUser(NameAndPasswd nameAndPasswd){
        User user = userService.getUserByName(nameAndPasswd.username);
        if (user == null || !user.getPassword().equals(nameAndPasswd.password))
            return null;
        return new UserInfo(user);
    }

    class NameAndPasswd {
        protected String username;
        protected String password;

        public NameAndPasswd (String name, String passwd){
            this.username = name;
            this.password = passwd;
        }
    }
}
