/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.server;

import com.google.gson.Gson;
import com.servicetool.control.ProcessAuthenSvs;
import com.servicetool.control.ProcessInputControl;
import com.servicetool.control.ProcessInputLister;
import com.servicetool.tcpserver.http.income.BaseIncomeRequestObject;
import com.servicetool.tcpserver.http.outcome.BaseOutComeObj;
import com.servicetool.utils.FuncUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.ServerCookieEncoder;
import io.netty.util.CharsetUtil;

import java.util.Set;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.codec.DecoderException;
import org.apache.log4j.Logger;

public class HttpSnoopServerHandler extends SimpleChannelInboundHandler<Object> {

    private HttpRequest request;
//    private DooService serviceBusiness;
    private final StringBuilder buf = new StringBuilder();

    private static Logger LOGGER;

    public HttpSnoopServerHandler() {
//        serviceBusiness = new DooService();
        LOGGER = Logger.getLogger("HttpSnoopServerHandler");
    }

    /**
     * Buffer that stores the response content
     */
//    private final StringBuilder buf = new StringBuilder();
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
//        StringBuilder buf = new StringBuilder();
        if (msg instanceof HttpRequest) {
            this.request = (HttpRequest) msg;

//            if (HttpHeaders.is100ContinueExpected(request)) {
//                send100Continue(ctx);
//            }
//            LOGGER.info("" + request.getUri());
//            System.out.println("" + request.getUri());
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
            Map<String, List<String>> params = queryStringDecoder.parameters();

            List<String> datas = null;
            for (Map.Entry<String, List<String>> entry : params.entrySet()) {
//                System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
                if (entry.getKey().equals("register")) {
                    datas = entry.getValue();
                    break;
                }
            }

            if (datas == null) {
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (String s : datas) {
                buf.append(s);
            }

            String data = buf.toString();
//            String cleanData = null;
//            try {
//                cleanData = FuncUtil.decrypt(data);
//            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | DecoderException ex) {
//                java.util.logging.Logger.getLogger(HttpSnoopServerHandler.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            if (cleanData == null) {
//                return;
//            }

            String[] dataParts = data.split(Pattern.quote(","));

            if (dataParts.length != 2) {
                return;
            }

//            if (!ProcessAuthenSvs.checkAuthen(dataParts[0], dataParts[1])) {
//                return;
//            }

            
            buf.setLength(0);
//            buf.append(serviceBusiness.genResponse(request.getUri()));

//            appendDecoderResult(buf, request);
        }

        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf content = httpContent.content();
            BaseIncomeRequestObject bio = null;

            if (content.isReadable()) {
                String data = content.toString(CharsetUtil.UTF_8);

                buf.append(data);

//                System.out.println("Data: " + data);
//                Gson gson = new Gson();
//                bio = gson.fromJson(data, BaseIncomeRequestObject.class);
//                buf.append("CONTENT: ");
//                buf.append(content.toString(CharsetUtil.UTF_8));
//                buf.append("\r\n");
//                appendDecoderResult(buf, request);
            }
//            else {
//                Gson gson = new Gson();
//                bio = gson.fromJson(buf.toString(), BaseIncomeRequestObject.class);
//                processIncomeData(bio, trailer, ctx);
//            }

            if (msg instanceof LastHttpContent) {
                Gson gson = new Gson();
                
                bio = gson.fromJson(buf.toString(), BaseIncomeRequestObject.class);

                LastHttpContent trailer = (LastHttpContent) msg;
                processIncomeData(bio, trailer, ctx);

                buf.setLength(0);

            }
        }
    }

    public void reponseResultToClient(int result, LastHttpContent trailer, ChannelHandlerContext ctx) {

        BaseOutComeObj baseOutComeObj = new BaseOutComeObj();
        baseOutComeObj.setResultCode(result);
        Gson gson = new Gson();

        if (!writeResponse(gson.toJson(baseOutComeObj), trailer, ctx)) {
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    public void reponseResultToClient(BaseOutComeObj baseOutComeObj, LastHttpContent trailer, ChannelHandlerContext ctx) {
        Gson gson = new Gson();

        if (!writeResponse(gson.toJson(baseOutComeObj), trailer, ctx)) {
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    public void reponseDataToClient(String responseData, LastHttpContent trailer, ChannelHandlerContext ctx) {
        if (!writeResponse(responseData, trailer, ctx)) {
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    public void processIncomeData(BaseIncomeRequestObject bio, LastHttpContent trailer, ChannelHandlerContext ctx) {

        if (bio == null) {
            return;
        }
        
        ProcessInputControl.processInput(bio, new ProcessInputLister() {
            @Override
            public void responseToClient(BaseOutComeObj baseOutComeObj) {
                reponseResultToClient(baseOutComeObj, trailer, ctx);
            }

            @Override
            public void responseToClient(int resultCode) {
                reponseResultToClient(resultCode, trailer, ctx);
            }
        });
    }

    private boolean writeResponse(String responseData, HttpObject currentObj, ChannelHandlerContext ctx) {
        // Decide whether to close the connection or not.
//        System.out.println("Response HTTP: " + responseData);
        boolean keepAlive = HttpHeaders.isKeepAlive(request);
        // Build the response object.
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, currentObj == null || currentObj.getDecoderResult().isSuccess() ? OK : BAD_REQUEST,
                Unpooled.copiedBuffer(responseData, CharsetUtil.UTF_8));

//        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.headers().set(CONTENT_TYPE, "application/json; charset=UTF-8");

        if (keepAlive) {
            // Add 'Content-Length' header only for a keep-alive connection.
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            // Add keep alive header as per:
            // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }

        // Encode the cookie.
        String cookieString = request.headers().get(COOKIE);
        if (cookieString != null) {
            Set<Cookie> cookies = CookieDecoder.decode(cookieString);
            if (!cookies.isEmpty()) {
                // Reset the cookies if necessary.
                for (Cookie cookie : cookies) {
                    response.headers().add(SET_COOKIE, ServerCookieEncoder.encode(cookie));
                }
            }
        } else {
            // Browser sent no cookie.  Add some.
            response.headers().add(SET_COOKIE, ServerCookieEncoder.encode("key1", "value1"));
            response.headers().add(SET_COOKIE, ServerCookieEncoder.encode("key2", "value2"));
        }

        // Write the response.
        ctx.write(response);

        return keepAlive;
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE);
        ctx.write(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
