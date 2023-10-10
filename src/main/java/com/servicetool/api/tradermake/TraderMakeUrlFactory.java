package com.servicetool.api.tradermake;

import com.servicetool.api.liverate.LiveRateConst;

public class TraderMakeUrlFactory {
    private static final String DOMAIN = "https://marketdata.tradermade.com/";

    public static String getTimeSeries(String currency, String startDate, String endDate) {

        return DOMAIN + "api/v1/timeseries?api_key=" + TraderMakeConst.API_KEY +
                "&currency=" + currency +
                "&start_date=" + startDate +
                "&end_date=" + endDate +
                "&format=records" +
                "&interval=hourly"
                ;
    }
}
