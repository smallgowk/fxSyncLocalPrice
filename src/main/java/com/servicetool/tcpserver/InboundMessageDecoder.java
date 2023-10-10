/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.tcpserver;

import com.google.gson.Gson;
import com.servicetool.utils.Constants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author duypn4
 */
public class InboundMessageDecoder extends MessageToMessageDecoder<Object> {

    private static Logger LOGGER;

    public InboundMessageDecoder(String tag) {
        LOGGER = Logger.getLogger(tag + ".MessageDecoder");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error(cause.getMessage());
        ctx.close();
        ctx.channel().close();
    }

    @Override
    protected void decode(ChannelHandlerContext chc, Object msg, List<Object> out) throws Exception {

        if (msg == null) {
            return;
        }

        FrameObject frameObject = (FrameObject) msg;

        if (frameObject.getAction().equals(Constants.TCPAction.CRAWL_DATA)) {
            if (!checkAuthen(frameObject)) {
                return;
            }
            processCrawlData(frameObject);
            
        } else {
            processAuthenData(frameObject);
        }
    }

    public boolean checkAuthen(FrameObject frameObject) {
        return true;
    }

    private void processAuthenData(FrameObject frameObject) {
    }

    public void processCrawlData(FrameObject frameObject) {
        switch (frameObject.getMarket()) {
            case Constants.MarketType.ALIEX:
                processAliexData(frameObject);
                break;
            case Constants.MarketType.BANGGOOD:
                processBanggoodData(frameObject);
                break;
        }
    }

    public void processAliexData(FrameObject frameObject) {
        switch (frameObject.getDataType()) {
            case Constants.DataType.STORE_COMMON:
//                processAliexStoreCommon(frameObject.getData());
                break;
            case Constants.DataType.STORE_PAGE:
                processAliexStorePage(frameObject.getData());
                break;
        }
    }

    public void processAliexStoreCommon(String data, String serial) {
        String cleanData = null;
//        try {
//            cleanData = EncryptUtil.decrypt(data);
//        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | DecoderException ex) {
//            java.util.logging.Logger.getLogger(InboundMessageDecoder.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        if(cleanData == null) {
            return;
        }
        
        
//        try {
//            ExcelUtils.initValidValues(aliexStoreCommon.getProductType(), aliexStoreCommon.getTempleFile());
//        } catch (IOException | InvalidFormatException ex) {
//            java.util.logging.Logger.getLogger(InboundMessageDecoder.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
//        DBUtil.createStoreInfo(aliexStoreCommon, serial);

//        try {
//            Utils.saveStoreInfoToCache(aliexStoreCommon, null, null);
//            System.out.println("Received Common: " + aliexStoreCommon.getCacheFile());
//        } catch (IOException ex) {
//            java.util.logging.Logger.getLogger(InboundMessageDecoder.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public void processAliexStorePage(String data) {
        
        String cleanData = null;
//        try {
//            cleanData = EncryptUtil.decrypt(data);
//        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | DecoderException ex) {
//            java.util.logging.Logger.getLogger(InboundMessageDecoder.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        if(cleanData == null) {
            return;
        }
        
        Gson gson = new Gson();
        
        

//        CrawlPageServerThread crawlPageServerThread = new CrawlPageServerThread();
//        crawlPageServerThread.start();
//        crawlPageServerThread.doGetListALiexProducts(aliexStoreCommon, aliexPageInfo);

    }

    public void processBanggoodData(FrameObject frameObject) {

    }

}
