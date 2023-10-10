package com.servicetool.api.liverate;

public class LiveRateUrlFactory {
    private static final String DOMAIN = "https://www.live-rates.com/";

    public static String getLivePriceRates(String pairs) {

        return DOMAIN + "api/price?key=" + LiveRateConst.ACCESS_TOKEN +
                "&rate=" + pairs ;
    }
}
