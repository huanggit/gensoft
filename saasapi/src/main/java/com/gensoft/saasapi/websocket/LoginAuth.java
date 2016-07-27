package com.gensoft.saasapi.websocket;

import com.gensoft.core.pojo.UserInfo;
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
        logInfo logInfo = decode(encryptedCode);
        return validateUser(logInfo);
    }

    private logInfo decode(String encryptedCode){
        String[] tmp = encryptedCode.split("\\+");
        logInfo result = new logInfo(tmp[0],tmp[1],null);
        return result;
//        if(tmp.length < 3) return null;
//         Long deviceId;
//        try{
//            deviceId = Long.parseLong(tmp[0]);
//        }catch (Exception e){
//            return null;
//        }
//        String password=tmp[1];
//        StringBuffer username = new StringBuffer();
//        for(int i=2;i<tmp.length;i++) {
//            username.append(tmp[i]);
//        }
//        logInfo result = new logInfo(username.toString(),password,deviceId);
//        return result;
    }

    private UserInfo validateUser(logInfo logInfo){
        if(logInfo==null)return null;
        User user = userService.getUserByName(logInfo.username);
        if (user == null || !user.getPassword().equals(logInfo.password))
            return null;
//        if(user.getBindDeviceId()!=logInfo.deviceid)
//            return null;
        return new UserInfo(user);
    }

    class logInfo {
        protected String username;
        protected String password;
        protected Long deviceid;

        public  logInfo(String name, String passwd, Long deviceid ){
            this.username = name;
            this.password = passwd;
            this.deviceid = deviceid;
        }
    }
}
