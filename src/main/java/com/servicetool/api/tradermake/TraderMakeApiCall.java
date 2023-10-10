package com.servicetool.api.tradermake;

import com.servicetool.api.ApiBase;

public class TraderMakeApiCall {
    public static TraderMakerResponse getTraderMakeTimeSeries(String currency, String startDate, String endDate) {
        String url = TraderMakeUrlFactory.getTimeSeries(currency, startDate, endDate);
        return ApiBase.getInstance().sendGet(url, TraderMakerResponse.class);
    }
}
