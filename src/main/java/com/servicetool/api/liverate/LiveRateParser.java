package com.servicetool.api.liverate;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.servicetool.model.Candle;

public class LiveRateParser {
    public static Candle parseLiveRateData(String response) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(response, Candle.class);
        } catch (JsonSyntaxException e) {
            System.out.println("Error parseLiveRateData: " + response);
            return null;
        }
    }
}
