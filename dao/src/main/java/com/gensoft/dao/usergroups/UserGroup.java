package com.gensoft.dao.usergroups;

import com.gensoft.dao.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by alan on 16-5-25.
 */
@Entity
@Table(name = "user_groups")
public class UserGroup extends BaseEntity{

    @NotNull
    private long userid;
    @NotNull
    private String name;
    @NotNull
    private String desc;
    @NotNull
    private long tagId;

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }
}
