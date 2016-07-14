package com.gensoft.saasapi.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gensoft.core.annotation.Login;
import com.gensoft.core.pojo.UserInfo;
import com.gensoft.core.web.ApiResult;
import com.gensoft.dao.chat.UserChatRepository;
import com.gensoft.saasapi.cache.UserInfoCache;
import com.gensoft.saasapi.controller.ChatController;
import com.gensoft.saasapi.controller.UserController;
import com.gensoft.saasapi.util.FileUtil;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.lang.annotation.Annotation;

/**
 * Created by alan on 16-7-12.
 */
@Service
public class CmdHandler extends ApplicationObjectSupport {

    @Autowired
    FileUtil fileUtil;

    @Autowired
    UserInfoCache userInfoCache;

    private ObjectMapper objectMapper = new ObjectMapper();


    public CmdRouter handleText(TextWebSocketFrame frame, UserInfo userInfo) throws IOException {
        String request = ((TextWebSocketFrame) frame).text();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap = objectMapper.readValue(request, paramMap.getClass());
        String cmd = (String)paramMap.get("cmd");
        Map<String, Object> params = ( Map<String, Object>)paramMap.get("params");
        Method method = methodMap.get(cmd);
        if (method == null)
            return null;
        try {
            Object[] args = getArgsByMethod(method, params, userInfo);
            Object methodClass = getApplicationContext().getBean(method.getDeclaringClass());
            Object result = method.invoke( methodClass, args);
            CmdRouter cmdRouter = null;
            if(result instanceof CmdRouter)
                cmdRouter = (CmdRouter) result;
            else if(result instanceof  ApiResult){
                ApiResult apiResult = (ApiResult)result;
                apiResult.setCmd(cmd);
                cmdRouter = new CmdRouter(result);
            }else
                cmdRouter = new CmdRouter(result);
            return cmdRouter;
        } catch (Exception e) {
            return null;
        }
    }

    public CmdRouter handleBinary(BinaryWebSocketFrame frame){
        ByteBuf buf = frame.content();
        byte[] content = new byte[buf.capacity()];
        buf.readBytes(content);
        String filePath = fileUtil.saveBytes(content);
        ApiResult apiResult = ApiResult.successInstance(filePath);
        apiResult.setCmd("uploadFile");
        return new CmdRouter(apiResult);
    }

    private Object[] getArgsByMethod(Method method, Map<String, Object> paramMap, UserInfo userInfo)
            throws Exception {
        Parameter[] params = method.getParameters();
        int argsNo = params.length;
        Object[] args = new Object[argsNo];
        for (int i = 0; i < argsNo; i++) {
            Parameter parameter = params[i];
            Annotation anno =  parameter.getAnnotations()[0];
            if(anno instanceof Login){
                args[i] =userInfo;
            } else if(anno instanceof RequestBody){
                String json = objectMapper.writeValueAsString(paramMap);
                args[i] = objectMapper.readValue(json, parameter.getType());
            } else if(anno instanceof RequestParam){
                Object value = paramMap.get(((RequestParam) anno).value());
                if(value instanceof Integer && parameter.getType().equals(Long.class))
                    value = Long.valueOf((Integer)value);
                args[i] = value;
            }
        }
        return args;
    }


    private static Map<String, Method> methodMap = new HashMap<>();
    private static Class<?>[] clzArray = { ChatController.class };

    static {
        for (Class<?> clz : clzArray)
            for (Method m : clz.getDeclaredMethods())
                methodMap.put(m.getName(), m);
    }





}
