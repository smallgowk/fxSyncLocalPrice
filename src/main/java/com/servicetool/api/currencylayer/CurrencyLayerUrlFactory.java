package com.servicetool.api.currencylayer;

public class CurrencyLayerUrlFactory {
    private static final String DOMAIN = "http://api.currencylayer.com/";

    public static String getLivePrice(String date, String currencies) {

        return DOMAIN + "live?access_key=" + CurrencyLayerConst.ACCESS_TOKEN +
                "&format=1" +
                "&date=" + date +
                "&currencies=" + currencies;
    }
}
