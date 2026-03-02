package com.example.vendor.netty.handlers;

import io.netty.channel.ChannelInboundHandler;

public interface MessageFrameDecoder {
    ChannelInboundHandler getHandler();
}