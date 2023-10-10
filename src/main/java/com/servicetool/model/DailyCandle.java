package com.servicetool.model;

import com.google.gson.Gson;
import com.servicetool.db.DBCandle;
import com.servicetool.db.ExecuteDBInterface;
import com.servicetool.utils.FileUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class DailyCandle extends H4Candle{

//    public static final int H4_TIME_FRAME = 4 * 60 * 60;
//    public static final int H1_TIME_FRAME = 60 * 60;

    public static final int H4_TIME_FRAME = 60 * 1000;
    public static final int H1_TIME_FRAME = 15 * 1000;

    public static final float TP_PIP = 0.002f;

//    public static long startTimeConfig;
//    public static long endTimeConfig;
//    public static int startHour = 9;
//    public static int startMinute = 3;

    public boolean isDone = false;

    public Candle currentMinuteCandle;

    public Calendar calendar;

//    static {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.AM_PM, 1);
//        calendar.set(Calendar.HOUR_OF_DAY, startHour);
//        calendar.set(Calendar.MINUTE, startMinute);
//        calendar.set(Calendar.SECOND, 0);
//        startTimeConfig = calendar.getTimeInMillis();
//        System.out.println("StartTimeConfig: " + startTimeConfig);
//
//        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 59);
//        calendar.set(Calendar.SECOND, 59);
//        endTimeConfig = calendar.getTimeInMillis();
//        System.out.println("EndTimeConfig: " + endTimeConfig);
//    }

    public DailyCandle() {
//        startTime = startTimeConfig;
//        endTime = endTimeConfig;
        calendar = Calendar.getInstance();
    }

    public DailyCandle(long timeStamp) {
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
    }

    public ArrayList<H4Candle> listH4Candles = new ArrayList<>();

    public void addH4Candles(ArrayList<H4Candle> list) {
        listH4Candles.addAll(list);
    }

    public void addNewCancel(Candle candle) {


//        System.out.println("Update minute candle!");

        currentMinuteCandle = new Candle();
        currentMinuteCandle.copy(candle);

//        H4Candle h4Candle = listH4Candles.stream()
//                .filter(h4 -> h4.isContainCandle(candle))
//                .findAny()
//                .orElse(null);
//        if (h4Candle != null) {
//            h4Candle.addNewCandle(candle);
//        } else {
//            DBCandle.insertH4Candle(currentH4, new ExecuteDBInterface() {
//                @Override
//                public void onResult(Object object) {
//                    currentH4.isInserted = true;
//                }
//
//                @Override
//                public void onError(SQLException ex) {
//
//                }
//            });
//            currentH4 = new H4Candle();
//            currentH4.copy(candle);
//            if (listH4Candles.isEmpty()) {
//                currentH4.setTime(startTime);
//            } else {
//                H4Candle lastCandle = listH4Candles.get(listH4Candles.size() - 1);
//                currentH4.setTime(lastCandle.endTime);
//            }
//            listH4Candles.add(currentH4);
//        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (H4Candle h4Candle : listH4Candles) {
            sb.append("H4Candle: ").append(h4Candle.openCloseInfo()).append("\n");
        }
        return sb.toString();
    }
}
