package com.servicetool.api.currencylayer.response;

import java.util.Map;

public class CurrencyApiModel {
    public long timestamp;
    public String source;
    public Map<String, Float> quotes;

    public Float getPriceRate(String instrument) {
        String key = new StringBuilder(source).append(instrument.toUpperCase()).toString();
        return quotes.get(key);
    }

    public Float getPrice(String instrument) {
        float rate = getPriceRate(instrument);
        return rate > 0 ? 1 / getPriceRate(instrument) : 0;
    }
}
