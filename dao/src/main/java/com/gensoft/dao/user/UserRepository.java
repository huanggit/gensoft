package com.gensoft.dao.user;

/**
 * Created by alan on 16-5-18.
 */

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends CrudRepository<User, Long> {

	@Query("select s from User s where status=1 and username=?1")
    public User findByUsername(String username);

	@Query("select s from User s where status=1 and mobile=?1")
    public List<User> getUserByMobile(long mobile);

    @Query("select s from User s where status=1")
    public List<User> findAllUsers();

    @Query("select s from User s where status=1 and nickname like :un")
    public  List<User> getUserFindLikeName(@Param("un") String likeName);

}