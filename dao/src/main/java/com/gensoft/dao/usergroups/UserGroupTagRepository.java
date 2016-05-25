package com.gensoft.dao.usergroups;

/**
 * Created by alan on 16-5-18.
 */

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserGroupTagRepository extends CrudRepository<UserGroupTag, Long> {


}