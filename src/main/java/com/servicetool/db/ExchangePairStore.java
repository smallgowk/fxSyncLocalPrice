package com.servicetool.db;

import com.servicetool.model.ExchangePair;

import java.util.ArrayList;

public class ExchangePairStore {
    private static ArrayList<ExchangePair> listDefaultExchangePairs;
    public static ArrayList<ExchangePair> listExchangePairs;

    public static void initDefault(ArrayList<ExchangePair> pairs) {
        listDefaultExchangePairs = new ArrayList<>();
        listDefaultExchangePairs.addAll(pairs);
        listExchangePairs = new ArrayList<>();
        listExchangePairs.addAll(pairs);
    }

    public static String getInstruments() {
        if (listExchangePairs == null || listExchangePairs.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (ExchangePair exchangePair : listExchangePairs) {
            if (sb.length() == 0) {
                sb.append(exchangePair.symbol);
            } else {
                sb.append(",").append(exchangePair.symbol);
            }
        }
        return sb.toString();
    }
}
