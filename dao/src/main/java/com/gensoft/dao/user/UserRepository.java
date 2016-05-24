package com.gensoft.dao.user;

/**
 * Created by alan on 16-5-18.
 */

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);

    @Query("select s from User s where id=?1")
    public User xx(Long x);

}