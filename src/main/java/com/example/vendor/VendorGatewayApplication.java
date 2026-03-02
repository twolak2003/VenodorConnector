package com.example.vendor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.vendor.netty.TcpServer;

@SpringBootApplication
public class VendorGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(VendorGatewayApplication.class, args);
        TcpServer server = new TcpServer(9000);
        System.out.println("getting things started");
        server.start();
    }
}