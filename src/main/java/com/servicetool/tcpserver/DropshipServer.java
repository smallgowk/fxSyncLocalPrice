/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.tcpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 *
 * @author duyuno
 */
public class DropshipServer {
    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    
    private Channel serverChannel;
    
    private int port;

    public DropshipServer(int port) {
        this.port = port;
    }
    
    
    public void startServer() {
        bossGroup = new NioEventLoopGroup(10);
        workerGroup = new NioEventLoopGroup(100);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            // Add more option for server
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.childOption(ChannelOption.SO_REUSEADDR, false);
//            bootstrap.childOption(ChannelOption.SO_TIMEOUT, 10);
            
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addFirst("timeout-handler", new IdleStateHandler(1200, 0, 0));
//                            ch.pipeline().addLast("ChannelStateHandle", new ChannelStateHandle());
                            ch.pipeline().addLast(new InboundFrameDecoder("ForexNettyServer"));
                            ch.pipeline().addLast(new InboundMessageDecoder("ForexNettyServer"));
                            ch.pipeline().addLast(new OutboundFrameEncoder("ForexNettyServer"));
//                            ch.pipeline().addLast(new OutboundMessageEncoder(Constants.MTR_SERVER_NAME));
//                            ch.pipeline().addLast(new InboundDataPrevent(Constants.MTR_SERVER_NAME));
//                            ch.pipeline().addLast(new InboundReconfigH1(Constants.MTR_SERVER_NAME));
////                            ch.pipeline().addLast(new InboundSendCommand(Constants.MTR_SERVER_NAME));
//                            ch.pipeline().addLast(new InboundDataHandler(Constants.MTR_SERVER_NAME));
                            
                        }
                    });

            // Bind and start to accept incoming connections.
            serverChannel  =  bootstrap.bind(port).sync().channel();
            System.out.println("Port: " + port + " ready!");
        } catch (InterruptedException ex) {
        }
    }
    
    public boolean isRunning() {
        if (null == serverChannel) {
            return false;
        }

        return serverChannel.isOpen();
    }
    
    public void stop() {
        if (isRunning()) {
            // Fire event for device specifice implementation
            onStopRequest();
            // Stop server channel
            serverChannel.close().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                }
            });
        } else {
            return;
        }

        
    }
    
    protected void onStopRequest() {
    }
}
