package com.servicetool.api.currencylayer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.servicetool.api.currencylayer.response.CurrencyApiModel;

public class CurrencyLayerParser {
    public static CurrencyApiModel parseCurrencyLayerLiveData(String response) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(response, CurrencyApiModel.class);
        } catch (JsonSyntaxException e) {
            System.out.println("Error parseProductDetailResponse: " + response);
            return null;
        }
    }
}
