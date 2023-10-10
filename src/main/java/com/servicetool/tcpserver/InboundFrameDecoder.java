/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.tcpserver;

import com.servicetool.utils.Constants.FrameHelper;
import com.servicetool.utils.PackageUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 *
 * @author duypn4
 */
public class InboundFrameDecoder extends ByteToMessageDecoder {

    private static Logger LOGGER;

    private volatile Channel outboundFWChannel;

    public static ChannelGroup allConnected = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public InboundFrameDecoder(String tag) {
        LOGGER = Logger.getLogger(tag + ".FrameDecoder");
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx); //To change body of generated methods, choose Tools | Templates.
        if (outboundFWChannel != null && outboundFWChannel.isActive()) {
            outboundFWChannel.close();
        }
    }

    @Override
    protected void decode(final ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {

        int readables = buffer.readableBytes();
        if (readables < FrameHelper.PACKAGE_START_MARK.length + FrameHelper.PACKAGE_END_MARK.length) {
            return;
        }
        byte[] arrByteBuf = new byte[readables];
        buffer.getBytes(0, arrByteBuf);

//        String data = new String(arrByteBuf);
//        LOGGER.info(data);
//        System.out.println("Ban tin nhan duoc: " + new String(arrByteBuf) + "|" + Hex.encodeHex(arrByteBuf));
        try {
            PackageUtil.packageMarkCheck(arrByteBuf, 0, FrameHelper.PACKAGE_START_MARK);
        } catch (CorruptedFrameException e) {
            LOGGER.error("Start mark khong dung dinh dang");
            throw e;
        }

        buffer.markReaderIndex();
        byte[] tmpMsgData = null;

        for (int i = 0, length = arrByteBuf.length; i < length; i++) {
            if (arrByteBuf[i] == FrameHelper.PACKAGE_END_MARK[0]) {
                if (i + FrameHelper.PACKAGE_END_MARK.length > length) {
                    break;
                }

                boolean isHitEndMark = true;
                for (int j = 0; j < FrameHelper.PACKAGE_END_MARK.length; j++) {
                    if (FrameHelper.PACKAGE_END_MARK[j] != arrByteBuf[i + j]) {
                        isHitEndMark = false;
                    }
                }
                if (!isHitEndMark) {
                    continue;
                }

                tmpMsgData = new byte[i - FrameHelper.PACKAGE_START_MARK.length];
                buffer.skipBytes(FrameHelper.PACKAGE_START_MARK.length);
                buffer.readBytes(tmpMsgData);
                buffer.skipBytes(FrameHelper.PACKAGE_END_MARK.length);
                break;
            }
        }

        if (tmpMsgData == null) {
            buffer.discardReadBytes();
            buffer.resetReaderIndex();
            return;
        }

        buffer.discardReadBytes();

        String msgData = new String(tmpMsgData);
//        String cleanDat = EncryptUtil.decrypt(msgString);
        System.out.println("" + msgData);
//        System.out.println("" + cleanDat);
        String[] msgString = (new String(tmpMsgData)).split(Pattern.quote(FrameHelper.SPLIT));

        FrameObject frameObject = new FrameObject();
        frameObject.setAction(msgString[0]);
        frameObject.setDiskSerialNumber(msgString[1]);
        frameObject.setVersion(msgString[2]);
        frameObject.setUserEmail(msgString[3]);

        if (msgString.length > 4) {
            frameObject.setMarket(msgString[4]);
//            String data = EncryptUtil.decrypt(msgString[5]);
//            System.out.println("Data: " + data);
            frameObject.setData(msgString[5]);
            frameObject.setDataType(Integer.parseInt(msgString[6]));
        }

        out.add(frameObject);

//        Gson gson = new Gson();
//        try {
//            
//            AliexStoreCommon aliexStoreCache = gson.fromJson(cleanDat, AliexStoreCommon.class);
//
//            if (aliexStoreCache != null && aliexStoreCache.getStoreId() != null) {
//                out.add(aliexStoreCache);
//            } else {
//                try {
//                    AliexPageInfo aliexPageInfo = gson.fromJson(cleanDat, AliexPageInfo.class);
//                    out.add(aliexPageInfo);
//                } catch (Exception ex1) {
//                    ex1.printStackTrace();
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            try {
//                AliexPageInfo aliexStoreCache = gson.fromJson(cleanDat, AliexPageInfo.class);
//                out.add(aliexStoreCache);
//            } catch (Exception ex1) {
//                ex1.printStackTrace();
//            }
//
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error(cause.getMessage());
        ctx.close();

        if (outboundFWChannel != null && outboundFWChannel.isActive()) {
            outboundFWChannel.close();
        }
    }

}
