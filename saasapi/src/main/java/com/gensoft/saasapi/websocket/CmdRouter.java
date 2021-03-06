package com.gensoft.saasapi.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gensoft.core.web.ApiResult;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alan on 16-7-12.
 */
public class CmdRouter {

    private ObjectMapper objectMapper = new ObjectMapper();

    private WebSocketFrame response;

    private List<Long> receivers;

    public WebSocketFrame getResponse() {
        return response;
    }

    public List<Long> getReceivers() {
        return receivers;
    }

    public CmdRouter(ApiResult apiResult, List<Long> receivers)  {
        this.receivers = receivers;
        try {
            this.response = new TextWebSocketFrame(objectMapper.writeValueAsString(apiResult));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public CmdRouter(ApiResult apiResult, Long receiver) {
        this.receivers = Arrays.asList(receiver);
        try {
            this.response = new TextWebSocketFrame(objectMapper.writeValueAsString(apiResult));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public CmdRouter(Object response) {
        try {
            this.response = new TextWebSocketFrame(objectMapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public CmdRouter(ByteBuf byteBuf) {
        this.response = new BinaryWebSocketFrame(byteBuf);
    }
}
