package com.gensoft.saasapi.pojo.user;

import javax.validation.constraints.NotNull;

/**
 * Created by alan on 16-5-25.
 */
public class LoginReq {
    @NotNull
    private String username;
    @NotNull
    private String password;

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
}
