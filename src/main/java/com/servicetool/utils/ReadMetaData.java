package com.servicetool.utils;

import com.servicetool.model.H1Candle;
import com.servicetool.model.MCandle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ReadMetaData {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    private static SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");

    public static ArrayList<H1Candle> readH1Data(String fileName, String currency) {
        BufferedReader br = null;
        FileInputStream fileInputStream = null;
        InputStreamReader clientSecretReader = null;
        ArrayList<H1Candle> listData = new ArrayList<>();
        try {
            fileInputStream = new FileInputStream(fileName);
            clientSecretReader = new InputStreamReader(fileInputStream);
            br = new BufferedReader(clientSecretReader);

            String st;
            while ((st = br.readLine()) != null) {
                if (!st.isEmpty()) {
//                    Pattern pattern = Pattern.compile("^[A-Za-z0-9., ]+");
                    st = st.trim().replaceAll("[^A-Za-z0-9.,: ]+", "");
                    String[] parts = st.trim().split(Pattern.quote(","));
                    if (parts.length >= 5) {
                        try {
                            Date date = sdf.parse(parts[0] + " " + parts[1]);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            calendar.setTime(date);
                            H1Candle h1Candle = new H1Candle();
                            h1Candle.setDate(sdfDB.format(date));
                            h1Candle.currency = currency;
                            h1Candle.open = formatValue(parts[2]);
                            h1Candle.high = formatValue(parts[3]);
                            h1Candle.low = formatValue(parts[4]);
                            h1Candle.close = formatValue(parts[5]);
                            h1Candle.hour = calendar.get(Calendar.HOUR_OF_DAY);
                            listData.add(h1Candle);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

        } catch (IOException ex) {
            System.out.println("" + ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(ReadMetaData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(ReadMetaData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (clientSecretReader != null) {
                try {
                    clientSecretReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(ReadMetaData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        return listData;
    }

    public static ArrayList<MCandle> readMData(String fileName, String currency) {
        BufferedReader br = null;
        FileInputStream fileInputStream = null;
        InputStreamReader clientSecretReader = null;
        ArrayList<MCandle> listData = new ArrayList<>();
        try {
            fileInputStream = new FileInputStream(fileName);
            clientSecretReader = new InputStreamReader(fileInputStream);
            br = new BufferedReader(clientSecretReader);

            String st;
            while ((st = br.readLine()) != null) {
                if (!st.isEmpty()) {
//                    Pattern pattern = Pattern.compile("^[A-Za-z0-9., ]+");
                    st = st.trim().replaceAll("[^A-Za-z0-9.,: ]+", "");
                    String[] parts = st.trim().split(Pattern.quote(","));
                    if (parts.length >= 5) {
                        try {
                            Date date = sdf.parse(parts[0] + " " + parts[1]);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            MCandle mCandle = new MCandle();
                            mCandle.setDate(sdfDB.format(date));
                            mCandle.currency = currency;
                            mCandle.open = formatValue(parts[2]);
                            mCandle.high = formatValue(parts[3]);
                            mCandle.low = formatValue(parts[4]);
                            mCandle.close = formatValue(parts[5]);
                            mCandle.hour = calendar.get(Calendar.HOUR_OF_DAY);
                            mCandle.minute = calendar.get(Calendar.MINUTE);
                            listData.add(mCandle);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

        } catch (IOException ex) {
            System.out.println("" + ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(ReadMetaData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(ReadMetaData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (clientSecretReader != null) {
                try {
                    clientSecretReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(ReadMetaData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        return listData;
    }

    public static String formatValue(String value) {
        if (value.length() <= 7) return value;
        return value.substring(0, 7);
    }
}
