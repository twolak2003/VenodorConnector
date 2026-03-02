package com.example.vendor.netty.handlers;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {
    public ProtocolFrameDecoder() {
        super(1024, 0, 4, 0, 4);
    }
}