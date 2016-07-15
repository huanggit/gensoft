package com.gensoft.saasapi.controller;

import com.gensoft.core.annotation.Login;
import com.gensoft.core.annotation.WebSocketController;
import com.gensoft.core.pojo.UserInfo;
import com.gensoft.core.web.ApiResult;
import com.gensoft.dao.chat.GroupChat;
import com.gensoft.dao.chat.LastChatRecord;
import com.gensoft.dao.chat.UserChat;
import com.gensoft.saasapi.pojo.chat.ChatMessage;
import com.gensoft.saasapi.service.ChatService;
import com.gensoft.saasapi.service.UserGroupService;
import com.gensoft.saasapi.websocket.CmdRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by alan on 16-7-5.
 */
@Component
@WebSocketController
public class ChatController {

    @Autowired
    ChatService chatService;

    @Autowired
    UserGroupService userGroupService;

    public List<LastChatRecord> chatsList(@Login UserInfo userInfo) {
        List<LastChatRecord> history = chatService.allChatHistory(userInfo.getId());
        return history;
    }

    public List<UserChat> userChatDetail(@Login UserInfo userInfo, @RequestParam("userId") Long userId) {
        List<UserChat> history = chatService.userChatHistoryByUserId(userInfo.getId(), userId);
        return history;
    }

    public List<GroupChat> groupChatDetail(@RequestParam("userId") Long groupId) {
        List<GroupChat> history = chatService.groupChatHistory(groupId);
        return history;
    }

    public CmdRouter userChat(@Login UserInfo userInfo, @RequestBody ChatMessage chatMessage) {
        chatService.saveUserChat(chatMessage, userInfo.getId());
        ApiResult apiResult = ApiResult.successInstance("userChat", chatMessage);
        CmdRouter cmdRouter = new CmdRouter(apiResult, chatMessage.getReceiverId());
        return cmdRouter;
    }

    public CmdRouter groupChat(@Login UserInfo userInfo, @RequestBody ChatMessage chatMessage) {
        chatService.saveGroupChat(chatMessage, userInfo.getId());
        ApiResult apiResult = ApiResult.successInstance("groupChat", chatMessage);
        List<Long> receivers = userGroupService.getGroupUsers(chatMessage.getReceiverId());
        CmdRouter cmdRouter = new CmdRouter(apiResult, receivers);
        return cmdRouter;
    }


}
