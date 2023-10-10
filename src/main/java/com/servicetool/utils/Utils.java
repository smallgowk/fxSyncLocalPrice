/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Admin
 */
public class Utils {

    public static HashMap<String, ArrayList<String>> hashMapMerchantKeys = new HashMap<>();
    
    public static final SimpleDateFormat sdfAppVersion = new SimpleDateFormat("yyyyMMdd'.'HHmm");
    public static final SimpleDateFormat sdfAppVersionDateOnly = new SimpleDateFormat("yyyyMMdd");

    public static String formatPrice(float price) {
        return String.format("%.2f", price);
    }

    public static String getCEOPrice(float price) {
        int n = (int) price;

        float ceoPrice = n * 1f + 0.99f;

        return formatPrice(ceoPrice);
    }

    public static String removeSpace(String input) {
        return input.replaceAll(Pattern.quote(" "), "");
    }

    public static String removeSpecialChar(String input) {
        return input.replaceAll("[^a-zA-Z0-9 ]", " ");
    }

    public static void writeToFile(String data, String filePath) throws IOException {
        
        File file = new File(filePath);
        if(!file.exists()) {
            file.createNewFile();
        }
//        String encryptData = null;
//        try {
//            encryptData = EncryptUtil.encrypt(data);
//        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
//            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
//        }
        

        FileOutputStream outputStream = new FileOutputStream(filePath);
        try (DataOutputStream dataOutStream = new DataOutputStream(new BufferedOutputStream(outputStream))) {
            dataOutStream.writeBytes(data);
        }
    }
    
    public static Date parseDate(String dateStr) {
        try {
            return dateStr.contains(".") ? sdfAppVersion.parse(dateStr) : sdfAppVersionDateOnly.parse(dateStr);
        } catch (ParseException ex) {
            System.out.println("" + ex.toString());
        }
        
        return null;
    }
    
    
}
