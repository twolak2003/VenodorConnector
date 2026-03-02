package com.example.vendor.netty;

import io.netty.channel.embedded.EmbeddedChannel;
import com.example.vendor.netty.handlers.HeartbeatHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TcpServerTest {
    @Test
    void testHeartbeatHandler() {
        EmbeddedChannel channel = new EmbeddedChannel(new HeartbeatHandler());
        channel.writeInbound("HEARTBEAT");
        assertTrue(channel.isOpen());
    }
}