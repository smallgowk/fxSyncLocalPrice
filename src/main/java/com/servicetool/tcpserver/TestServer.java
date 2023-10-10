/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.tcpserver;

/**
 *
 * @author duyuno
 */
public class TestServer {
    public static void main(String[] str) throws Exception {
        DropshipServer dropshipServer = new DropshipServer(9669);
        dropshipServer.startServer();
    }
}
