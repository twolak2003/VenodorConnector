package com.example.vendor.netty.handlers;

import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartbeatHandler extends ChannelDuplexHandler {
    private int missedHeartbeats = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent e) {
            if (e.state() == IdleState.WRITER_IDLE) ctx.writeAndFlush("HEARTBEAT\n");
            else if (e.state() == IdleState.READER_IDLE) {
                missedHeartbeats++;
                if (missedHeartbeats > 3) ctx.close();
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if ("HEARTBEAT".equals(msg.toString())) { missedHeartbeats = 0; return; }
        super.channelRead(ctx, msg);
    }
}