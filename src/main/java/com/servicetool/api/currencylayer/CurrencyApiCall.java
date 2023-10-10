package com.servicetool.api.currencylayer;

import com.servicetool.api.ApiBase;
import com.servicetool.api.currencylayer.response.CurrencyApiModel;

public class CurrencyApiCall {
    public static CurrencyApiModel getLivePrice(String date, String currencies) {
        String url = CurrencyLayerUrlFactory.getLivePrice(date, currencies);
        return ApiBase.getInstance().sendGet(url, CurrencyApiModel.class);
    }
}
