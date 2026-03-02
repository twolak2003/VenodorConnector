package com.example.vendor.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import com.example.vendor.netty.handlers.*;

public class TcpServer {
    private final int port;
    public TcpServer(int port) { this.port = port; }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChannelInitializer<Channel>() {
                 @Override
                 protected void initChannel(Channel ch) {
                     ch.pipeline().addLast(
                         new IdleStateHandler(60, 30, 0),
                         new ProtocolFrameDecoder(),
                             new io.netty.handler.codec.string.StringDecoder(),
                             new io.netty.handler.codec.string.StringEncoder(),
                         new HeartbeatHandler(),
                         new BusinessRouterHandler()
                     );
                 }
             });
            ChannelFuture f = b.bind(port).sync();
            System.out.println("TCP server started on port " + port);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}