package com.servicetool.api.model;

import com.google.gson.Gson;
import com.servicetool.model.Candle;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Rate {
    public String currency;
    public String bid;
    public String ask;
    public String high;
    public String low;
    public String open;
    public String close;
    public String timestamp;

    public Candle toCandle() {
        Candle candle = new Candle();
        candle.currency = currency;
        candle.bid = bid;
        candle.ask = ask;
        candle.high = high;
        candle.low = low;
        candle.open = open;
        candle.close = close;
//        long t = parseLongValue(timestamp);
//        candle.timestamp = convertTimeZone(t);
        candle.timestamp = parseLongValue(timestamp);
        return candle;
    }

    private float parseFloatValue(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private long parseLongValue(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private long convertTimeZone(long timeStamp) {
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
//        calendar.setTimeInMillis(timeStamp);
        if (currency.equals("BTC/USD")) {
            System.out.println("Calendar Origin: " + new Date(timeStamp));
        }
//
//        calendar.setTimeZone(TimeZone.getTimeZone("UTC+7"));
//        if (currency.equals("EUR/USD")) {
//            System.out.println("Calendar UTC+07: " + new Date(calendar.getTimeInMillis()));
//        }
        long convert = timeStamp + 7 * 60 * 60 * 1000;
        if (currency.equals("BTC/USD")) {
            System.out.println("Calendar Converted: " + new Date(convert));
        }
        return convert;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
