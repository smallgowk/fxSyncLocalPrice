/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.utils;

import com.servicetool.config.Configs;
import com.servicetool.control.FileHandler;
import com.servicetool.control.FileWatcher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PhanDuy
 */
public class FileUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
    
    public static void addWatcher(String folderPath, FileHandler fileHandler) {
        Path path = Paths.get(folderPath);
        
//        FileHandler fileHandler = (File file, WatchEvent.Kind<?> fileEvent) -> {
//            init();
//        };

        try {
            FileWatcher fileWatcher = new FileWatcher(path, fileHandler, false, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

            Thread watcherThread = new Thread(fileWatcher);
            watcherThread.start();

        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void saveData(String data, String fileName) {
        File file = new File(Configs.TOOL_DATA_PATH + Configs.pathChar + sdf.format(new Date()));
        if (!file.exists()) {
            file.mkdir();
        }


//        File file = new File(Configs.TOOL_DATA_PATH + Configs.pathChar + fileName);
//        try {
//            FileUtils.writeStringToFile(file, data);
//            ArrayList<String> list = new ArrayList<>();
//            list.add(data);
//            FileUtils.writeLines(file, list);
//
//        } catch (IOException ex) {
//            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try (PrintWriter output = new PrintWriter(new FileWriter(file.getAbsolutePath() + Configs.pathChar + fileName, true))) {
            output.printf("%s\r\n", data);
        } catch (Exception ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getFileName(String pair) {
        return pair.replace("/", "_") + ".txt";
    }

    public static String getFileNameSignal(String pair) {
        return "Signal_" + pair.replace("/", "_") + ".txt";
    }
}
