/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.config;

import static com.servicetool.config.AppConfig.APP_FOLDER;
import com.servicetool.control.FileHandler;
import com.servicetool.utils.FileUtil;
import com.servicetool.utils.OSUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.WatchEvent;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author duyuno
 */
public class Configs {

    public static final String CONFIG_DIRECTORY = "AppConfig";
    public static final String DATA_DIRECTORY = "Data";
    public static final String CONFIGS_DIRECTORY = "Configs";
    
    public static final String LOG_DIR = "../logs";
    
    public static String TOOL_DATA_PATH;

    public static String CONFIG_FOLDER_PATH;

    public static String pathChar;

    public static String appIconPath;

    public static String serverPort;
    public static String contentPort;

    public static void init() {

        if (OSUtil.isWindows()) {
            pathChar = "\\";
        } else {
            pathChar = "/";
        }

        appIconPath = "Images" + pathChar + "appIcon.png";

        TOOL_DATA_PATH = "etc" + pathChar + DATA_DIRECTORY;
        CONFIG_FOLDER_PATH = "etc" + pathChar + CONFIG_DIRECTORY;
        
        initServerConfig();
        
        File directory = new File(TOOL_DATA_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File configDirectory = new File(CONFIG_FOLDER_PATH);
        if (!configDirectory.exists()) {
            configDirectory.mkdir();
        } 
        
        AppConfig.folderPath = CONFIG_FOLDER_PATH + APP_FOLDER;
        
        System.out.println("" + AppConfig.folderPath);
        
        AppConfig.init();
        
        FileUtil.addWatcher(AppConfig.folderPath, new FileHandler() {
            @Override
            public void handle(File file, WatchEvent.Kind<?> fileEvent) throws InterruptedException {
                AppConfig.init();
            }
        });
    }

    public static void initServerConfig() {
        Properties cnfParamsTmp = new Properties();
        FileInputStream propsFile = null;
        try {
            propsFile = new FileInputStream(CONFIG_FOLDER_PATH + pathChar + "server.conf");
            cnfParamsTmp.load(new InputStreamReader(propsFile, Charset.forName("UTF-8")));
            serverPort = cnfParamsTmp.getProperty("serverPort", "69");
            contentPort = cnfParamsTmp.getProperty("contentPort", "89");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (propsFile != null) {
                try {
                    propsFile.close();
                } catch (IOException e) {
                    // thong bao dong file loi
                }
            }
        }
    }
    

    public static boolean isEmptyPath(String path) {
        return path == null || path.trim().isEmpty();
    }

//    public static void initDefaulPaths() {
////        boolean isEndWithPathChar = TOOL_DATA_PATH.endsWith(pathChar);
//        PRODUCT_DATA_PATH = String.valueOf(TOOL_DATA_PATH + (TOOL_DATA_PATH.endsWith(pathChar) ? "" : pathChar) + DEFAULT_PRODUCT_DIRECTORY + pathChar);
//        STORE_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + STORE_PRODUCT_DIRECTORY + pathChar);
//        BRANCH_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + BRANCH_PRODUCT_DIRECTORY + pathChar);
//        FIXING_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + FIXING_PRODUCT_DIRECTORY + pathChar);
//    }

    public static void changeConfigValues(String fileName, String[]... listConfigs) throws IOException {
        if (listConfigs == null || listConfigs.length == 0) {
            return;
        }

//        InputStream inputStream = Configs.class.getResourceAsStream("/" + fileName);
//        InputStreamReader clientSecretReader = new InputStreamReader(inputStream);
        FileInputStream in = new FileInputStream(CONFIG_FOLDER_PATH + fileName);
        Properties props = new Properties();
        props.load(in);
        in.close();

//        ClassLoader classLoader = NewMainUI.class.getClassLoader();
//        String pathStr = classLoader.getResource(fileName).getFile();
//        System.out.println("Setting for " + pathStr);
        FileOutputStream out = new FileOutputStream(CONFIG_FOLDER_PATH + fileName);
        for (String[] pair : listConfigs) {
            if (pair[0] == null || pair[0].isEmpty() || pair[1] == null || pair[1].isEmpty()) {
//                System.out.println("" + pair[0]);
//                System.out.println("" + pair[1]);
                continue;
            }

            props.setProperty(pair[0], pair[1]);

        }
        props.store(out, null);
        out.close();

//        String localStr = pathStr.replace("target/classes", "src/main/resources");
////        System.out.println("Setting for " + localStr);
//        FileOutputStream outLocal = new FileOutputStream(localStr);
//        for (String[] pair : listConfigs) {
//            if (pair[0] == null || pair[0].isEmpty() || pair[1] == null || pair[1].isEmpty()) {
//                continue;
//            }
//            props.setProperty(pair[0], pair[1]);
//
//        }
//        props.store(outLocal, null);
//        outLocal.close();
    }
    
    

    public static void copyConfigsFilesByName(String fileName) throws IOException {
        
        File file = new File(CONFIG_FOLDER_PATH + fileName);
        if(file.exists()) {
            return;
        }
        
        InputStream inputStream = Configs.class.getResourceAsStream("/" + fileName);
//        InputStreamReader clientSecretReader = new InputStreamReader(inputStream);
//
//        Properties props = new Properties();
//        props.load(clientSecretReader);
//        clientSecretReader.close();
//        inputStream.close();

//        FileOutputStream outLocal = new FileOutputStream(CONFIG_FOLDER_PATH + fileName);
//        props.store(outLocal, null);
//        outLocal.close();
        FileOutputStream outLocal = null;
        try {
            outLocal = new FileOutputStream(CONFIG_FOLDER_PATH + fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            outLocal.write(buffer);

//            File file = new File(CONFIG_FOLDER_PATH + fileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (outLocal != null) {
                try {
                    outLocal.close();
                } catch (IOException ex) {
                    Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void copyExecuteFilesByName(String fileName) {
        
        File file = new File(CONFIG_FOLDER_PATH + fileName);
        if(file.exists()) {
            return;
        }
        
        InputStream inputStream = null;
        FileOutputStream outLocal = null;

        inputStream = Configs.class.getResourceAsStream("/" + fileName);

        if(inputStream == null) {
            return;
        }
        
//        InputStreamReader clientSecretReader = new InputStreamReader(inputStream);
        try {
            outLocal = new FileOutputStream(CONFIG_FOLDER_PATH + fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            outLocal.write(buffer);

            file = new File(CONFIG_FOLDER_PATH + fileName);
            file.setExecutable(true);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (outLocal != null) {
                try {
                    outLocal.close();
                } catch (IOException ex) {
                    Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
