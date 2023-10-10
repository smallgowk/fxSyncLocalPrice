/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.server.process;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author PhanDuy
 */
public class ProcessManager {
    private static ProcessManager processManager;
    
    ExecutorService executor = Executors.newFixedThreadPool(10);
    
    public static ProcessManager getInstance() {
        if(processManager == null) {
            processManager = new ProcessManager();
        }
        
        return processManager;
    }
    
    public void executeTask(Runnable runnable) {
    }
}
