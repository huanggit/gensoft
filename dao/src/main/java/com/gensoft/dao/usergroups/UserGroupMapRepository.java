package com.gensoft.dao.usergroups;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

/**
 * Created by alan on 16-5-18.
 */


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserGroupMapRepository extends CrudRepository<UserGroupMap, Long> {


}