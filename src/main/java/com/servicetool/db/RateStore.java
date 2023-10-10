package com.servicetool.db;

import com.servicetool.model.*;
import com.servicetool.server.FirebaseManager;

import java.util.HashMap;

public class RateStore {
    public static final float TP_PIP = 0.002f;

    public static HashMap<String, HashMap<String, HashMap<Integer, H1Candle>>> hashMapDateH1Candles = new HashMap<>();
    public static HashMap<String, HashMap<String, DailyCandle>> hashMapDailyCandles = new HashMap<>();
    public static HashMap<String, H1Candle> hashMapPairCandle = new HashMap<>();

    public static H1Candle getCandle(String pair) {
        return hashMapPairCandle.get(pair);
    }

    public static void putCandle(String pair, H1Candle candle) {
        hashMapPairCandle.put(pair, candle);
    }

    public static void putH1Candle(H1Candle h1Candle) {
        HashMap<String, HashMap<Integer, H1Candle>> hashMapCurrency = hashMapDateH1Candles.computeIfAbsent(h1Candle.date, k -> new HashMap<>());
        HashMap<Integer, H1Candle> hashMapHourCandle = hashMapCurrency.computeIfAbsent(h1Candle.currency, k -> new HashMap<>());
        hashMapHourCandle.put(h1Candle.hour, h1Candle);
    }

    public static H1Candle getH1Candle(String date, String currency, int hour) {
        HashMap<String, HashMap<Integer, H1Candle>> hashMapCurrency = hashMapDateH1Candles.get(date);
        if (hashMapCurrency == null) return null;
        HashMap<Integer, H1Candle> hashMapHourCandle = hashMapCurrency.get(currency);
        if (hashMapHourCandle == null) return null;
        return hashMapHourCandle.get(hour);
    }

    public static float getLowValue(String date, String currency, int fromHour, int toHour) {
        float low = 0;

        return low;
    }
}
