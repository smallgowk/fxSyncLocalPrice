/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.control;

import java.io.File;
import java.nio.file.WatchEvent.Kind;
import java.util.logging.Logger;

/**
 *
 * @author PhanDuy
 */
public class FileHandlerTest implements FileHandler {

    private static final Logger LOGGER = Logger.getLogger(FileHandlerTest.class.getName());

    /*
	 * This implemented method will delete the file
	 * 
	 * @see com.io.util.FileHandler#handle(java.io.File,
	 * java.nio.file.WatchEvent.Kind)
     */
    @Override
    public void handle(File file, Kind<?> fileEvent) {
        
//        LOGGER.log(Level.INFO, "Handler is triggered for file {0}", file.getPath());
//        System.out.println("fileEvent: " + fileEvent.name());
//        if (fileEvent == StandardWatchEventKinds.ENTRY_CREATE) {
//            try {
//                boolean deleted = Files.deleteIfExists(Paths.get(file.getPath()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

}
