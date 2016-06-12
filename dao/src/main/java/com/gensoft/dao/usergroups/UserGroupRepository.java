package com.gensoft.dao.usergroups;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by alan on 16-5-18.
 */

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {

	@Query("select s from UserGroup s where userid=?1")
	public List<UserGroup>  getMyGroup(long userId );
	
	@Transactional
	@Modifying
	@Query("delete  from UserFriend  where userId=?1 and friendId=?2")
    public int delUserFriendByUid(long userId,long friendId);
	
}