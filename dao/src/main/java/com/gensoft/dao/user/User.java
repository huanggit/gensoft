package com.gensoft.dao.user;

/**
 * Created by alan on 16-5-18.
 */

import com.gensoft.dao.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private Integer status;
    @NotNull
    private String nickname;
    @NotNull
    private Long mobile;

    private String logo;

    private Long bindDeviceId;

    //

} // class User
