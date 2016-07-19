package com.gensoft.saasapi.websocket;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

//This is heartbeat sent by server, not used by now.
//Now use HeartBeatController, sent by client.
public class HeartBeatHandler extends ChannelHandlerAdapter {

    private final long heartbeatIntervalSec;

    private boolean isClosed = false;

    public HeartBeatHandler(long heartbeatIntervalSec) {
        this.heartbeatIntervalSec = heartbeatIntervalSec;
    }

    private void initialize(ChannelHandlerContext ctx) {
        ctx.executor().schedule(new SendHeartbeatTask(ctx),
                heartbeatIntervalSec, TimeUnit.SECONDS);
    }

    private final class SendHeartbeatTask implements Runnable {
        private final ChannelHandlerContext ctx;
        SendHeartbeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }
        @Override
        public void run() {
            if (isClosed)
                return;
            ctx.writeAndFlush("hb");
            initialize(ctx);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        initialize(ctx);
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        isClosed = true;
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        isClosed = true;
        super.channelInactive(ctx);
    }

}
