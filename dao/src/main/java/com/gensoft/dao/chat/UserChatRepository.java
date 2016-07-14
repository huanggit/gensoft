package com.gensoft.dao.chat;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by alan on 16-7-6.
 */
public interface UserChatRepository extends CrudRepository<UserChat,Long> {

    @Query("select c from UserChat c where (c.senderId=?1 and c.receiverId=?2) or (c.senderId=?2 and c.receiverId=?1)")
    List<UserChat> chatHistoryBySingleUser(Long myId, Long userId);

}
