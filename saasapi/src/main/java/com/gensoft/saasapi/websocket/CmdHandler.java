package com.gensoft.saasapi.websocket;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gensoft.core.annotation.Login;
import com.gensoft.core.annotation.WebSocketController;
import com.gensoft.core.pojo.UserInfo;
import com.gensoft.core.web.ApiResult;
import com.gensoft.core.web.BusinessException;
import com.gensoft.saasapi.cache.UserInfoCache;
import com.gensoft.saasapi.controller.*;
import com.gensoft.saasapi.util.FileUtil;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
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
public class CmdHandler extends ApplicationObjectSupport implements InitializingBean{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FileUtil fileUtil;

    @Autowired
    UserInfoCache userInfoCache;

    private ObjectMapper objectMapper = new ObjectMapper();


    public CmdRouter handleText(TextWebSocketFrame frame, UserInfo userInfo) {
        Long userId = userInfo.getId();
        CmdRouter cmdRouter = null;
        String request = frame.text();
        logger.info("[request] user: {}, json: {}.", userId, request);
        Map<String, Object> paramMap = new HashMap<>();
        try {
            paramMap = objectMapper.readValue(request, paramMap.getClass());
            String cmd = (String) paramMap.get("cmd");
            try {
                Object result = handle(cmd, paramMap, userInfo);
                if (result == null) {
                    new CmdRouter(ApiResult.failedInstance(cmd, ApiResult.CODE_RETURN_OBJECT_IS_NULL));
                }else if (result instanceof ByteBuf) {
                    cmdRouter = new CmdRouter((ByteBuf)result);
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
            logResponse(cmdRouter, userId);
        } catch (IOException e) {
            logger.info("[response] user: {}, bad json format for request: {}.", userId, request);
            cmdRouter = new CmdRouter(ApiResult.failedInstance("bad_json_format_request", ApiResult.CODE_INVALID_JSON_FORMAT_REQUEST));
        }
        return cmdRouter;
    }

    private Object handle(String cmd, Map<String, Object> paramMap, UserInfo userInfo)
            throws InvocationTargetException, IllegalAccessException, BusinessException, IOException {
        Map<String, Object> params = (Map<String, Object>) paramMap.get("params");
        Method method = methodMap.get(cmd);
        if (method == null)
            throw new BusinessException(ApiResult.CODE_INVALID_CMD);
        Object[] args = getArgsByMethod(method, params, userInfo);

        Object methodClass = getApplicationContext().getBean(method.getDeclaringClass());
        Object result = method.invoke(methodClass, args);
        return result;
    }

    private void logResponse(CmdRouter cmdRouter, Long userId){
        WebSocketFrame responsFrame = cmdRouter.getResponse();
        if(responsFrame instanceof TextWebSocketFrame) {
            logger.info("[response] user: {}, json: {}, receiver: {}.", userId,
                    ((TextWebSocketFrame) responsFrame).text(), cmdRouter.getReceivers());
        }else if(responsFrame instanceof BinaryWebSocketFrame) {
            logger.info("[response] user: {}, file path, receiver: {}.", userId, cmdRouter.getReceivers());
        }
    }

    public CmdRouter handleBinary(BinaryWebSocketFrame frame, UserInfo userInfo) {
        CmdRouter cmdRouter = null;
        try {
            ByteBuf buf = frame.content();
            byte[] content = new byte[buf.capacity()];
            buf.readBytes(content);
            String filePath = fileUtil.saveBytes(content);
            cmdRouter = new CmdRouter(ApiResult.successInstance("uploadFile", filePath));
        } catch (BusinessException e) {
            cmdRouter = new CmdRouter(ApiResult.failedInstance("uploadFile", e.getCode()));
        }
        WebSocketFrame responsFrame = cmdRouter.getResponse();
        logger.info("[response] user: {}, json: {}, receiver: {}.", userInfo.getId(), ((TextWebSocketFrame)responsFrame).text(), cmdRouter.getReceivers());
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

    private Map<String, Method> methodMap = new HashMap<>();
    private Map<Method, Object> methodBeanMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Object> beans = getApplicationContext().getBeansWithAnnotation(WebSocketController.class);
        for(Object bean: beans.values()){
            for (Method m : bean.getClass().getDeclaredMethods()) {
                methodMap.put(m.getName(), m);
                methodBeanMap.put(m, bean);
            }
        }
    }
}
