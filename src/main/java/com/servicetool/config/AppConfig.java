/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.config;

import static com.servicetool.config.Configs.*;
import com.servicetool.utils.Utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author PhanDuy
 */
public class AppConfig {

    public static final String APP_FOLDER = "/Apps";
    private static final String CONTENT_CONFIG_FILE = "appConfig.txt";

    public static String lastestVersion;
    public static Date lastestVersionDate;

    public static String contentPort;

    public static String folderPath;

    public static void init() {

        File file = new File(folderPath + pathChar + CONTENT_CONFIG_FILE);
        System.out.println("AppConfig: " + file.getPath());
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader clientSecretReader = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(clientSecretReader);
            String st;
            while ((st = br.readLine()) != null) {
                if (st != null && !st.isEmpty()) {

//                    System.out.println("" + st);
                    String[] parts = st.split(Pattern.quote("="));

                    System.out.println("" + Arrays.toString(parts) + " | " + parts.length);

                    if (parts.length == 2 && st.contains("lastestVersion")) {

                        if (st.contains("lastestVersion")) {
                            lastestVersion = parts[1];
                            System.out.println("lastestVersion: " + lastestVersion);
                            lastestVersionDate = Utils.parseDate(lastestVersion);
                            System.out.println("lastestVersionDate: " + lastestVersionDate);
                        } else if(st.contains("contentPort")){
                            contentPort = parts[1];
                        }
                    }

                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("" + ex.toString());
        } catch (IOException ex) {
            System.out.println("" + ex.toString());
        }
    }

    public static boolean isValidVersionDate(Date date) {
        return date.equals(lastestVersionDate) || date.after(lastestVersionDate);
    }
}
