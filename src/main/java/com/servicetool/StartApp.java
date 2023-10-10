/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool;

import com.servicetool.config.Configs;
import com.servicetool.db.*;
import com.servicetool.server.FirebaseManager;
import com.servicetool.server.HttpSnoopServer;
import com.servicetool.server.process.ProcessPriceThread;
import com.servicetool.tcpserver.DropshipServer;
import com.servicetool.utils.Constants.ConfigPaths;
import org.apache.log4j.PropertyConfigurator;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

import java.beans.PropertyVetoException;
import java.io.IOException;

/**
 *
 * @author duyuno
 */
public class StartApp implements WrapperListener{
    DropshipServer dropshipServer;
    
    HttpSnoopServer httpSnoopServer;
    
    
    public static void main(String args[]) {
        // Config duong dan tro den file cau hinh cua log4j
        PropertyConfigurator.configure(ConfigPaths.LOG4J_PROPERTY);

        // Start wrapper
        WrapperManager.start(new StartApp(), args);
//        ProcessUpdatePriceThread thread = new ProcessUpdatePriceThread();
//        thread.startListen();
//        thread.start();
    }
    
    @Override
    public Integer start(String[] strings) {
        Configs.init();

        try {
            ForexDDConnectionPool.getInstance().initC3P0();
            System.out.println("Init db connect success!");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        DBAuthenUtil.init();

        ProcessPriceThread processPriceThread = new ProcessPriceThread();
        processPriceThread.start();
        
        return null;
    }

    @Override
    public int stop(int i) {
//        dropshipServer.stop();
        return i;
    }

    @Override
    public void controlEvent(int i) {
    }
}
