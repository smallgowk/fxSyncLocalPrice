package com.servicetool.server.process;

import com.google.gson.Gson;
import com.servicetool.api.tradermake.TraderMakeConst;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

public class TraderMakeSocketIoThread {
    Socket socket = null;
    Gson gson = new Gson();
    public TraderMakeSocketIoThread() {
        try {
            socket = IO.socket("https://marketdata.tradermade.com");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void connect() {
        socket.on("connect",connect);
        socket.on("handshake",handshake);
        socket.on("subResponse",subResponse);
        socket.on("message",message);
        socket.on("price",price);
        socket.on("login",login);
        socket.on("disconnect",disconnect);
        socket.connect();
    }

    private Emitter.Listener login = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("logined");
            socket.emit("symbolSub", gson.toJson(new TraderMakerSymbol("BTCUSD")));
            socket.emit("symbolSub", gson.toJson(new TraderMakerSymbol("GBPUSD")));
        }
    };

    private Emitter.Listener connect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = gson.toJson(new TraderMakerIOLogin(TraderMakeConst.SOCKET_KEY));
            System.out.println("Connected: " + data);
            socket.emit("login", data);
        }
    };

    private Emitter.Listener disconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("Disconencted");
        }
    };

    private Emitter.Listener handshake = args -> {
        System.out.println("handshaked");
        socket.emit("symbolSub", gson.toJson(new TraderMakerSymbol("BTCUSD")));
        socket.emit("symbolSub", gson.toJson(new TraderMakerSymbol("GBPUSD")));
    };

    private Emitter.Listener subResponse = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("subResponse: " + args);
        }
    };

    private Emitter.Listener message = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("message: " + args);
        }
    };

    private Emitter.Listener price = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("price: " + args);
        }
    };
}
