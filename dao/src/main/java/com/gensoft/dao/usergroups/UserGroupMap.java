package com.gensoft.dao.usergroups;

import com.gensoft.dao.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by alan on 16-5-25.
 */
@Entity
@Table(name = "user_group_map")
public class UserGroupMap extends BaseEntity{

    @NotNull
    private long groupId;
    @NotNull
    private long userId;

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
