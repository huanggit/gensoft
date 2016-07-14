package com.gensoft.core.pojo;

import com.gensoft.dao.user.User;

import java.io.Serializable;

/**
 * Created by alan on 16-5-25.
 */
public class UserInfo implements Serializable{


    public UserInfo(User user) {
        this.id = user.getId();
        this.userName = user.getUsername();
        this.nickName = user.getNickname();
    }

    public UserInfo() {
    }

    private Long id;
//    private Long deviceId;
    private String userName;
    private String nickName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getDeviceId() {
//        return deviceId;
//    }
//
//    public void setDeviceId(Long deviceId) {
//        this.deviceId = deviceId;
//    }
}
