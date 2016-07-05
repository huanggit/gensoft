package com.gensoft.dao.userfriends;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by alan on 16-5-25.
 */
public interface UserFriendRepository extends CrudRepository<UserFriend,Long>{
	@Query("select s from UserFriend s where userId=?1")
    public List<UserFriend> getUserFriendByUid(long userid);
	
	@Modifying
	@Query("delete  from UserFriend  where userId=?1 and friendId=?2")
    public int delUserFriendByUid(long userId,long friendId);
	
	
}
