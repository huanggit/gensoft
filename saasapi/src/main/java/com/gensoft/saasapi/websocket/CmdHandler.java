package com.gensoft.saasapi.websocket;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gensoft.core.annotation.Login;
import com.gensoft.core.pojo.UserInfo;
import com.gensoft.core.web.ApiResult;
import com.gensoft.core.web.BusinessException;
import com.gensoft.saasapi.cache.UserInfoCache;
import com.gensoft.saasapi.controller.ChatController;
import com.gensoft.saasapi.controller.UserController;
import com.gensoft.saasapi.controller.UserFriendsController;
import com.gensoft.saasapi.controller.UserGroupController;
import com.gensoft.saasapi.util.FileUtil;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FileUtil fileUtil;

    @Autowired
    UserInfoCache userInfoCache;

    private ObjectMapper objectMapper = new ObjectMapper();


    public CmdRouter handleText(TextWebSocketFrame frame, UserInfo userInfo) {
        CmdRouter cmdRouter = null;
        String request = frame.text();
        logger.info("[request by {}]: {}.", userInfo.getId(), request);
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap = objectMapper.readValue(request, paramMap.getClass());
            String cmd = (String) paramMap.get("cmd");
            try {
                Map<String, Object> params = (Map<String, Object>) paramMap.get("params");
                Method method = methodMap.get(cmd);
                if (method == null)
                    throw new BusinessException(ApiResult.CODE_INVALID_CMD);
                Object[] args = getArgsByMethod(method, params, userInfo);
                Object methodClass = getApplicationContext().getBean(method.getDeclaringClass());
                Object result = method.invoke(methodClass, args);
                if (result == null) {
                    new CmdRouter(ApiResult.failedInstance(cmd, ApiResult.CODE_RETURN_OBJECT_IS_NULL));
                } else if (result instanceof CmdRouter) {
                    cmdRouter = (CmdRouter) result;
                } else if (result instanceof ApiResult) {
                    ApiResult apiResult = (ApiResult) result;
                    apiResult.setCmd(cmd);
                    cmdRouter = new CmdRouter(result);
                } else {
                    cmdRouter = new CmdRouter(ApiResult.successInstance(cmd, result));
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                cmdRouter = new CmdRouter(ApiResult.failedInstance(cmd, ApiResult.CODE_INVALID_PARAMS));
            } catch (BusinessException e) {
                cmdRouter = new CmdRouter(ApiResult.failedInstance(cmd, e.getCode()));
            } catch (Exception e) {
                logger.error("[bug]", e);
                cmdRouter = new CmdRouter(ApiResult.failedInstance(cmd, ApiResult.CODE_UNHANDLED_BUG));
            }
            logger.info("[response by {}]: {}.", userInfo.getId(), objectMapper.writeValueAsString(cmdRouter));
        } catch (IOException e) {
            logger.info("[response by {}]: bad json format for request: {}.", userInfo.getId(), request);
            cmdRouter = new CmdRouter(ApiResult.failedInstance("bad_json_format_request", ApiResult.CODE_INVALID_JSON_FORMAT_REQUEST));
        }
        return cmdRouter;
    }

    public CmdRouter handleBinary(BinaryWebSocketFrame frame, UserInfo userInfo) {
        CmdRouter cmdRouter = null;
        try {
            try {
                ByteBuf buf = frame.content();
                byte[] content = new byte[buf.capacity()];
                buf.readBytes(content);
                String filePath = fileUtil.saveBytes(content);
                cmdRouter = new CmdRouter(ApiResult.successInstance("uploadFile", filePath));
            } catch (BusinessException e) {
                cmdRouter = new CmdRouter(ApiResult.failedInstance("uploadFile", e.getCode()));
            }
            logger.info("[response by {}]: {}.", userInfo.getId(), objectMapper.writeValueAsString(cmdRouter));
        } catch (JsonProcessingException e) {
        }
        return cmdRouter;
    }

    private Object[] getArgsByMethod(Method method, Map<String, Object> paramMap, UserInfo userInfo) throws IOException {
        Parameter[] params = method.getParameters();
        int argsNo = params.length;
        Object[] args = new Object[argsNo];
        for (int i = 0; i < argsNo; i++) {
            Parameter parameter = params[i];
            Annotation anno = parameter.getAnnotations()[0];
            if (anno instanceof Login) {
                args[i] = userInfo;
            } else if (anno instanceof RequestBody) {
                String json = objectMapper.writeValueAsString(paramMap);
                args[i] = objectMapper.readValue(json, parameter.getType());
            } else if (anno instanceof RequestParam) {
                Object value = paramMap.get(((RequestParam) anno).value());
                if (value instanceof Integer && parameter.getType().equals(Long.class))
                    value = Long.valueOf((Integer) value);
                args[i] = value;
            }
        }
        return args;
    }


    private static Map<String, Method> methodMap = new HashMap<>();
    private static Class<?>[] clzArray = {ChatController.class, UserController.class, UserFriendsController.class, UserGroupController.class};

    static {
        for (Class<?> clz : clzArray)
            for (Method m : clz.getDeclaredMethods())
                methodMap.put(m.getName(), m);
    }


}
