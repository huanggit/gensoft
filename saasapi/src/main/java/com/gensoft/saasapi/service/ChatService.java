package com.gensoft.saasapi.service;

import com.gensoft.dao.chat.*;
import com.gensoft.saasapi.pojo.chat.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by alan on 16-7-6.
 */
@Service
public class ChatService {

    @Autowired
    private UserChatRepository userChatRepository;

    @Autowired
    GroupChatRepository groupChatRepository;

    @Autowired
    LastChatRecordRepository lastChatRecordRepository;

    @Transactional
    public void saveUserChat(ChatMessage chatMessage, Long senderid){
        UserChat userChat = new UserChat();
        userChat.setCreateDate(new Date());
        userChat.setIsVoice(chatMessage.getIsVoice());
        userChat.setMessage(chatMessage.getContent());
        userChat.setReceiverId(chatMessage.getReceiverId());
        userChat.setSenderId(senderid);
        userChatRepository.save(userChat);
        savelastChatRecord(chatMessage, senderid, false);
    }

    @Transactional
    public void saveGroupChat(ChatMessage chatMessage, Long senderid){
        GroupChat groupChat = new GroupChat();
        groupChat.setCreateDate(new Date());
        groupChat.setIsVoice(chatMessage.getIsVoice());
        groupChat.setGroupId(chatMessage.getReceiverId());
        groupChat.setSenderId(senderid);
        groupChat.setMessage(chatMessage.getContent());
        groupChatRepository.save(groupChat);
        savelastChatRecord(chatMessage, senderid, true);
    }

    private void savelastChatRecord(ChatMessage chatMessage, Long senderid, boolean isGroup){
        LastChatRecord lastChatRecord = new LastChatRecord();
        lastChatRecord.setMessage(chatMessage.getContent());
        lastChatRecord.setIsVoice(chatMessage.getIsVoice());
        lastChatRecord.setCreateDate(new Date());
        lastChatRecord.setChatId(chatMessage.getReceiverId());
        lastChatRecord.setUserId(senderid);
        lastChatRecord.setIsGroup(isGroup?1:0);
        lastChatRecordRepository.save(lastChatRecord);
    }

    public List<UserChat>  userChatHistoryByUserId(Long myId, Long userId){
        return userChatRepository.chatHistoryBySingleUser(myId,userId);
    }

    public List<GroupChat> groupChatHistory(Long groupId){
        return groupChatRepository.findByGroupId(groupId);
    }

    public List<LastChatRecord> allChatHistory(Long userId){
        return lastChatRecordRepository.allChatHistory(userId);
    }

}
