package com.gensoft.dao.user;

/**
 * Created by alan on 16-5-18.
 */

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
	@Query("select s from User s where username=?1")
    public User findByUsername(String username);
	@Query("select s from User s where mobile=?1")
    public User getUserByMobile(long mobile);
	
    @Query("select s from User s where id=?1")
    public User xx(Long x);
    @Query("select s from User s where nickname like :un")
    public  List<User> getUserFindLikeName(@Param("un") String likeName);

}