package com.servicetool.model;

import java.util.ArrayList;

public class MCandle extends Candle{
    public long startTime;
    public long endTime;
    public int hour;
    public int minute;
    public String date;
    public ArrayList<Candle> listCandles = new ArrayList<>();

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addCandle(Candle candle) {
        listCandles.add(candle);
    }

    public void addNewCandle(Candle candle) {
        listCandles.add(candle);
    }

    @Override
    public String toString() {
        return "M5Candle{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", date='" + date + '\'' +
                ", currency='" + currency + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", high=" + high +
                ", low=" + low +
                ", open=" + open +
                ", close=" + close +
                '}';
    }
}
