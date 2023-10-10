package com.servicetool.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class H1Candle extends Candle{
    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public long startTime;
    public long endTime;
    public int hour;
    public String date;
    public ArrayList<Candle> listCandles = new ArrayList<>();

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(long startTime) {
        this.startTime = startTime;
        this.endTime = startTime + DailyCandle.H1_TIME_FRAME;
    }


    public void addCandle(Candle candle) {
        listCandles.add(candle);
    }

    public void addNewCandle(Candle candle) {
        listCandles.add(candle);
    }

    public static H1Candle fromResultSet(ResultSet resultSet, Date date) throws SQLException {
        H1Candle h1Candle = new H1Candle();
        h1Candle.open = resultSet.getString(1);
        h1Candle.close = resultSet.getString(2);
        h1Candle.low = resultSet.getString(3);
        h1Candle.high = resultSet.getString(4);
        h1Candle.bid = resultSet.getString(5);
        h1Candle.ask = resultSet.getString(6);
        h1Candle.currency = resultSet.getString(7);
        h1Candle.hour = resultSet.getInt(8);
        h1Candle.date = sdf.format(date);
        return h1Candle;
    }

    public static ArrayList<H1Candle> listFromResultSet(ResultSet resultSet, Date date) {
        ArrayList<H1Candle> list = null;
        try {
            while (resultSet.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(H1Candle.fromResultSet(resultSet, date));
            }
        } catch (SQLException throwables) {

        }
        return list;
    }

    @Override
    public String toString() {
        return "H1Candle{" +
                "hour=" + hour +
                ", date='" + date + '\'' +
                ", currency='" + currency + '\'' +
                ", high=" + high +
                ", low=" + low +
                ", open=" + open +
                ", close=" + close +
                '}';
    }
}
