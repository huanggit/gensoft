package com.gensoft.dao.userfriends;

import com.gensoft.dao.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by alan on 16-5-25.
 */
@Entity
@Table(name = "user_friends")
public class UserFriend extends BaseEntity{

    @NotNull
    private long userId;
    @NotNull
    private long friendId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }
}
