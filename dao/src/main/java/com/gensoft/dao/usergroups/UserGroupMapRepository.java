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
public interface UserGroupMapRepository extends CrudRepository<UserGroupMap, Long> {

	@Modifying
	@Query("delete  from UserGroupMap  where groupId=?1 ")
    public int delUserGroupMapById(long groupId);

	@Query("select userId from UserGroupMap  where groupId=?1")
	public List<Long> getGroupUsers(long groupId);
	
}