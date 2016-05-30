package com.gensoft.dao.userfriends;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by alan on 16-5-25.
 */
public interface UserFriendRepository extends CrudRepository<UserFriend,Long>{
	@Query("select s from UserFriend s where userid=?1")
    public List<UserFriend> getUserFriendByUid(long userid);
}
