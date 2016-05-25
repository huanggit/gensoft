package com.gensoft.dao.user;

/**
 * Created by alan on 16-5-18.
 */

import com.gensoft.dao.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String nickname;
    @NotNull
    private Long mobile;

    private String logo;

    private Long bindDeviceId;

    private String plateNo;

    //setter & getters
    public String getPlateNo() { return plateNo; }

    public void setPlateNo(String plateNo) { this.plateNo = plateNo; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Long getBindDeviceId() {
        return bindDeviceId;
    }

    public void setBindDeviceId(Long bindDeviceId) {
        this.bindDeviceId = bindDeviceId;
    }
} // class User
