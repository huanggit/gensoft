package com.gensoft.dao.chat;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by alan on 16-7-6.
 */
public interface GroupChatRepository  extends CrudRepository<GroupChat,Long> {

    public List<GroupChat> findByGroupId(Long groupId);
}
