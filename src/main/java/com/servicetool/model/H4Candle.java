package com.servicetool.model;

import com.servicetool.db.DBCandle;
import com.servicetool.db.ExecuteDBInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class H4Candle extends H1Candle{
    public ArrayList<H1Candle> listH1Candles = new ArrayList<>();
    public H1Candle currentH1;

    public void setTime(long startTime) {
        this.startTime = startTime;
        this.endTime = startTime + DailyCandle.H4_TIME_FRAME;
    }


    public void addH1Candle(H1Candle h1Candle) {
        listH1Candles.add(h1Candle);
    }

    public void addH1Candles(ArrayList<H1Candle> list) {
        listH1Candles.addAll(list);
    }

    public void info(int index) {
        for (int i = 0, size = listH1Candles.size(); i < size; i++) {
            System.out.println("H4-" + index + "  H1" + (i + 1) + " " + listH1Candles.get(i).listCandles.size());
        }
    }
}
