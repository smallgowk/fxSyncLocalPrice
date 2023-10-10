/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.tcpserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.log4j.Logger;

/**
 *
 * @author duypn4
 */
public class OutboundFrameEncoder extends MessageToByteEncoder<Object> {

    private static Logger LOGGER;

    public OutboundFrameEncoder(String tag) {
        LOGGER = Logger.getLogger(tag + ".FrameEncoder");
    }

//    @Override
//    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
//        if (msg instanceof FrameEncodeMessageObject) {
//            out.writeBytes(((FrameEncodeMessageObject) msg).getMessage());
//            ctx.writeAndFlush(out);
//            LOGGER.info(((FrameEncodeMessageObject) msg).getImei() + " | Gui xuong client: " + new String(((FrameEncodeMessageObject) msg).getMessage()));
//        } else {
//            throw new Exception("FrameEncode invalid");
//        }
//    }
    
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof FrameEncodeMessageObject) {
            byte[] message = ((FrameEncodeMessageObject) msg).getMessage();
            ByteBuf out = ctx.alloc().buffer(message.length);
            out.writeBytes(message);
            ctx.writeAndFlush(out);
        } 
        else {
            System.out.println("FrameEncode invalid");
            throw new Exception("FrameEncode invalid");
        }
    }
    
    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}
