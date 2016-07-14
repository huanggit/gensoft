package com.gensoft.saasapi.controller;

import com.gensoft.core.annotation.AnonymousAccess;
import com.gensoft.core.annotation.Login;
import com.gensoft.core.pojo.UserInfo;
import com.gensoft.core.web.ApiResult;
import com.gensoft.dao.chat.GroupChat;
import com.gensoft.dao.chat.LastChatRecord;
import com.gensoft.dao.chat.UserChat;
import com.gensoft.dao.chat.UserChatRepository;
import com.gensoft.dao.user.User;
import com.gensoft.dao.usergroups.UserGroup;
import com.gensoft.dao.usergroups.UserGroupMapRepository;
import com.gensoft.dao.usergroups.UserGroupRepository;
import com.gensoft.saasapi.pojo.chat.ChatMessage;
import com.gensoft.saasapi.pojo.user.RegisterReq;
import com.gensoft.saasapi.service.ChatService;
import com.gensoft.saasapi.service.UserGroupService;
import com.gensoft.saasapi.util.FileUtil;
import com.gensoft.saasapi.websocket.CmdRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alan on 16-7-5.
 */
@Component
public class ChatController {

    @Autowired
    ChatService chatService;

    @Autowired
    UserGroupService userGroupService;

    public ApiResult chatsList(@Login UserInfo userInfo) {
        List<LastChatRecord> history = chatService.allChatHistory(userInfo.getId());
        return ApiResult.successInstance(history);
    }

    public ApiResult userChatDetail(@Login UserInfo userInfo, @RequestParam("userId") Long userId) {
        List<UserChat> history = chatService.userChatHistoryByUserId(userInfo.getId(),userId);
        return ApiResult.successInstance(history);
    }

    public ApiResult groupChatDetail(@RequestParam("userId") Long groupId) {
        List<GroupChat> history = chatService.groupChatHistory(groupId);
        return ApiResult.successInstance(history);
    }

    public CmdRouter userChat(@Login UserInfo userInfo, @RequestBody ChatMessage chatMessage){
        chatService.saveUserChat(chatMessage, userInfo.getId());
        ApiResult apiResult = ApiResult.successInstance(chatMessage);
        apiResult.setCmd("userChat");
        CmdRouter cmdRouter = new CmdRouter(apiResult, chatMessage.getReceiverId());
        return  cmdRouter;
    }

    public CmdRouter groupChat(@Login UserInfo userInfo,  @RequestBody ChatMessage chatMessage){
        chatService.saveGroupChat(chatMessage, userInfo.getId());
        ApiResult apiResult = ApiResult.successInstance(chatMessage);
        apiResult.setCmd("groupChat");
        List<Long> receivers = userGroupService.getGroupUsers(chatMessage.getReceiverId());
        CmdRouter cmdRouter = new CmdRouter(apiResult, receivers);
        return  cmdRouter;
    }


}
