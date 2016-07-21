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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by alan on 16-7-5.
 */
@Component
@WebSocketController
public class SystemController {

    public String gps(@Login UserInfo userInfo, @RequestParam("lat") Double latitude, @RequestParam("lan") Double longitude) {
        //// TODO: 16-7-19 save gsp info
        //System.out.println(latitude+","+longitude);
        return "ok";
    }

}
