/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PhanDuy
 */
public class ComputerIdentifier {
    
    public static String diskSerial;

    public static String getDiskSerialNumberForWindow() throws IOException, InterruptedException {
        
        if(diskSerial != null) {
            return diskSerial;
        }
        
        String sc = "cmd /c" + "wmic diskdrive get serialnumber";

        Process p = Runtime.getRuntime().exec(sc);
        p.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        
        diskSerial = sb.substring(sb.toString().lastIndexOf("r") + 1).trim();

        return diskSerial;
    }

    public static String getDiskSerialNumberForLinux() throws IOException, InterruptedException {
        String sc = "/sbin/udevadm info --query=property --name=sda"; // get HDD parameters as non root user
        String[] scargs = {"/bin/sh", "-c", sc};

        Process p = Runtime.getRuntime().exec(scargs);
        p.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            if (line.indexOf("ID_SERIAL_SHORT") != -1) { // look for ID_SERIAL_SHORT or ID_SERIAL
                sb.append(line);
            }
        }

        return sb.toString().substring(sb.toString().indexOf("=") + 1);
    }
    
    public static String getDiskSerialNumber() {
        try {
            return OSUtil.isWindows() ? getDiskSerialNumberForWindow() : getDiskSerialNumberForLinux();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ComputerIdentifier.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return null;
    }
    
    public static String getComputerName() {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME")) {
            return env.get("COMPUTERNAME");
        } else if (env.containsKey("HOSTNAME")) {
            return env.get("HOSTNAME");
        } else {
            return "Unknown Computer";
        }
    }

//    public static void main(String[] arguments) {
//        String identifier = generateLicenseKey();
//        System.out.println(identifier);
//    }
}
