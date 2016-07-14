package com.gensoft.dao.chat;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by alan on 16-7-6.
 */
public interface LastChatRecordRepository extends CrudRepository<LastChatRecord,Long> {

    @Query("select c from LastChatRecord c where c.userId=?1")
    List<LastChatRecord> allChatHistory(Long userId);

}
