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
        System.out.println("客户端与服务端连接开启");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端与服务端连接关闭");
        userInfoCache.removeChannel(ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
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

        if (!"websocket".equals(request.headers().get("Upgrade"))) {
            ChannelFuture f = channel.writeAndFlush(new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            f.addListener(ChannelFutureListener.CLOSE);
            return;
        }

        WebSocketServerHandshakerFactory wsShakerFactory = new WebSocketServerHandshakerFactory(
                "ws://" + request.headers().get(HttpHeaderNames.HOST), null, false);

        String url = request.uri();
        String authInfo = url.substring(1);
        UserInfo userInfo = loginAuth.authUser(authInfo);

        if (userInfo == null) {
            wsShakerFactory.sendUnsupportedVersionResponse(channel);
            logger.info("ip {} failed login.", userIp);
            return;
        }

        WebSocketServerHandshaker wsShakerHandler = wsShakerFactory.newHandshaker(request);
        if (null == wsShakerHandler) {
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
        String clientIP = request.headers().get("X-Forwarded-For").toString();
        if (clientIP == null) {
            InetSocketAddress insocket = (InetSocketAddress) channel.remoteAddress();
            clientIP = insocket.getAddress().getHostAddress();
        }
        return clientIP;
    }


    private void handlerWebSocketFrame(ChannelHandlerContext ctx,
                                       WebSocketFrame frame) throws IOException {
        Channel channel = ctx.channel();
        UserInfo userInfo = userInfoCache.getUserByChannel(channel);
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            channel.write(new PongWebSocketFrame(frame.content().retain()));
            return;
        } else {
            if (frame instanceof TextWebSocketFrame) {
                CmdRouter cmdRouter = cmdHandler.handleText((TextWebSocketFrame) frame, userInfo);
                routerResponse(cmdRouter, channel);

            } else if (frame instanceof BinaryWebSocketFrame) {
                CmdRouter cmdRouter = cmdHandler.handleBinary((BinaryWebSocketFrame) frame, userInfo);
                routerResponse(cmdRouter, channel);

            } else if (frame instanceof CloseWebSocketFrame) {
//            wsShakerHandler.close(ctx.channel(), (CloseWebSocketFrame) frame
//                    .retain());
            }
        }
    }

    private void routerResponse(CmdRouter cmdRouter, Channel channel) {
        TextWebSocketFrame response = cmdRouter.getResponse();
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
