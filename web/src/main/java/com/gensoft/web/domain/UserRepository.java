package com.gensoft.web.domain;

/**
 * Created by alan on 16-5-18.
 */

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByName(String name);

    @Query("select s from User s where id=?1")
    public User xx(Long x);

}