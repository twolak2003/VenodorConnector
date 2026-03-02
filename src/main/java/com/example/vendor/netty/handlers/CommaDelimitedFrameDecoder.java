package com.example.vendor.netty.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Splits messages by comma ',' delimiter
 */
public class CommaDelimitedFrameDecoder extends ByteToMessageDecoder implements MessageFrameDecoder {

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int readableBytes = in.readableBytes();
        if (readableBytes == 0) return;

        int index = in.forEachByte(value -> value != ',');
        if (index >= 0) {
            int length = index - in.readerIndex();
            ByteBuf frame = in.readBytes(length);
            in.readByte(); // skip comma
            out.add(frame);
        }
    }

    @Override
    public ChannelInboundHandler getHandler() {
        return this;
    }
}