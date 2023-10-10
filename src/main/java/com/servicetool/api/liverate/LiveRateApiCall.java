package com.servicetool.api.liverate;

import com.servicetool.api.ApiBase;
import com.servicetool.api.liverate.response.PriceRateResponse;
import com.servicetool.model.ExchangePair;

import java.util.ArrayList;

public class LiveRateApiCall {
    public static PriceRateResponse getLivePriceRates(String pairs) {
        String url = LiveRateUrlFactory.getLivePriceRates(pairs);
        return ApiBase.getInstance().sendGet(url, PriceRateResponse.class);
    }

    public static String getLivePriceRatesWithStrResult(String pairs) {
        String url = LiveRateUrlFactory.getLivePriceRates(pairs);
        return ApiBase.getInstance().sendGetWithStrResponse(url);
    }
}
