package com.gensoft.saasapi.websocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import com.gensoft.core.pojo.UserInfo;

import com.gensoft.saasapi.cache.UserInfoCache;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


/**
 * Created by alan on 16-7-11.
 */
@Service
@ChannelHandler.Sharable
public class WebSocketCharServerHandler extends SimpleChannelInboundHandler<Object> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoginAuth loginAuth;

    @Autowired
    UserInfoCache userInfoCache;

    @Autowired
    CmdHandler cmdHandler;

    //private WebSocketServerHandshaker wsShakerHandler;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("ip {} channel active.", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("ip {} channel inactive.", ctx.channel().remoteAddress());
        userInfoCache.removeChannel(ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        logger.error("channel exception: {}.", cause);
        userInfoCache.removeChannel(ctx.channel());
        ctx.close();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, ((FullHttpRequest) msg));
        } else if (msg instanceof WebSocketFrame) {
            handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx,
                                   FullHttpRequest request) {
        Channel channel = ctx.channel();
        String userIp = getIpAddress(channel, request);
        logger.debug("ip {} attempt to connect to the server.", userIp);
//        if (!"websocket".equals(request.headers().get("Upgrade"))) {
//            logger.debug("ip {} don't have Upgrade http header.", userIp);
//            ChannelFuture f = channel.writeAndFlush(new DefaultFullHttpResponse(
//                    HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
//            f.addListener(ChannelFutureListener.CLOSE);
//            return;
//        }

        WebSocketServerHandshakerFactory wsShakerFactory = new WebSocketServerHandshakerFactory(
                "ws://" + request.headers().get(HttpHeaderNames.HOST), null, false);

        String url = request.uri();
        String authInfo = url.substring(1);
        UserInfo userInfo = loginAuth.authUser(authInfo);

        if (userInfo == null) {
            logger.info("ip {} failed login.", userIp);
            ChannelFuture f = channel.writeAndFlush(new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            f.addListener(ChannelFutureListener.CLOSE);
            return;
        }

        WebSocketServerHandshaker wsShakerHandler = wsShakerFactory.newHandshaker(request);
        if (null == wsShakerHandler) {
            logger.debug("ip {} use the websocket of an unsupported version.", userIp);
            //无法处理的websocket版本
            wsShakerFactory.sendUnsupportedVersionResponse(channel);
        } else {
            //向客户端发送websocket握手,完成握手
            wsShakerHandler.handshake(channel, request);
        }
        logger.info("ip {} success loged in as user {} (id:{}).", userIp, userInfo.getUserName(), userInfo.getId());
        userInfoCache.addChannel(channel, userInfo);
    }

    private String getIpAddress(Channel channel, FullHttpRequest request) {
        String clientIP = null;
        try {
            clientIP = String.valueOf(request.headers().get("X-Forwarded-For"));
            if (clientIP == null) {
                InetSocketAddress insocket = (InetSocketAddress) channel.remoteAddress();
                clientIP = insocket.getAddress().getHostAddress();
            }
        } catch (Exception e) {

        }
        return clientIP;
    }


    private void handlerWebSocketFrame(ChannelHandlerContext ctx,
                                       WebSocketFrame frame) throws IOException {
        Channel channel = ctx.channel();
        UserInfo userInfo = userInfoCache.getUserByChannel(channel);
        if (frame instanceof TextWebSocketFrame) {
            CmdRouter cmdRouter = cmdHandler.handleText((TextWebSocketFrame) frame, userInfo);
            routerResponse(cmdRouter, channel);
        } else if (frame instanceof BinaryWebSocketFrame) {
            CmdRouter cmdRouter = cmdHandler.handleBinary((BinaryWebSocketFrame) frame, userInfo);
            routerResponse(cmdRouter, channel);
        }
//        else if (frame instanceof PingWebSocketFrame) {
//            channel.write(new PongWebSocketFrame(frame.content().retain()));
//            return;
//            // 判断是否ping消息
//        } else if (frame instanceof CloseWebSocketFrame) {
//            // wsShakerHandler.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
//        }
    }

    private void routerResponse(CmdRouter cmdRouter, Channel channel) {
        WebSocketFrame response = cmdRouter.getResponse();
        List<Long> receivers = cmdRouter.getReceivers();
        if (CollectionUtils.isEmpty(receivers)) {
            channel.writeAndFlush(response);
        } else {
            for (Long userId : receivers) {
                Channel ch = userInfoCache.getChannelByUserId(userId);
                if (ch != null)
                    ch.writeAndFlush(response);
            }
        }
    }
}
