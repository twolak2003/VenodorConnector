package com.example.vendor.netty.handlers;

import com.example.vendor.webflux.HttpServiceClient;
import io.netty.channel.*;
import io.netty.channel.SimpleChannelInboundHandler;
import reactor.core.publisher.Mono;

public class BusinessRouterHandler extends SimpleChannelInboundHandler<String> {
    private final HttpServiceClient httpClient = new HttpServiceClient();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("Received from vendor: " + msg);

        Mono<String> responseMono = httpClient.sendRequest(msg);

        responseMono.subscribe(response -> {
            System.out.println("HTTP response: " + response);
            ctx.writeAndFlush(response + "\n");
        }, err -> {
            err.printStackTrace();
            ctx.writeAndFlush("ERROR\n");
        });
    }
}