/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.server;

import com.servicetool.config.Configs;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An HTTP server that sends back the content of the received HTTP request in a
 * pretty plaintext form.
 */
public final class HttpSnoopServer {

    static final boolean SSL = System.getProperty("ssl") != null;
//    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8443" : "8080"));
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "443" : "80"));
    
    public void init() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(10);
        EventLoopGroup workerGroup = new NioEventLoopGroup(100);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.childOption(ChannelOption.TCP_NODELAY, true);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);
            b.childOption(ChannelOption.SO_REUSEADDR, false);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpSnoopServerInitializer());

            Channel ch = b.bind(PORT).sync().channel();
            
            System.out.println("Port: " + PORT);
            
//            System.err.println("Open your web browser and navigate to "
//                    + (SSL ? "https" : "http") + "://127.0.0.1:" + PORT + '/');

//            ch.closeFuture().sync();
        } catch (InterruptedException ex) {
            Logger.getLogger(HttpSnoopServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
        }
    }
}
