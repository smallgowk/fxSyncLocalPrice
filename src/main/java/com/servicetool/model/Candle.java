package com.servicetool.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Candle {
    public String currency;
    public String bid;
    public String ask;
    public String high;
    public String low;
    public String open;
    public String close;
    public long timestamp;
    public boolean isInserted = false;

    public boolean isDojiCandle() {
        return close == open;
    }

    public void copy(Candle candle) {
        this.currency = candle.currency;
        this.bid = candle.bid;
        this.ask = candle.ask;
        this.high = candle.high;
        this.low = candle.low;
        this.open = candle.open;
        this.close = candle.close;
        this.timestamp = candle.timestamp;
    }

    public String openCloseInfo() {
        return "{" +
                "open=" + open +
                ", close=" + close +
                "}";
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Candel{" +
                "currency='" + currency + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", high=" + high +
                ", low=" + low +
                ", open=" + open +
                ", close=" + close +
                ", date=" + new Date(timestamp) +
                '}';
    }


    @Override
    public int hashCode() {
        return Objects.hash(currency, bid, ask, high, low, open, close, timestamp);
    }



    public int getH1Hour(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int calculateH4Index(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour / 4;
    }

    public Calendar getH4Calendar(int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, index * 4);
        return calendar;
    }

    public Calendar getH1Calendar(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return calendar;
    }
}
