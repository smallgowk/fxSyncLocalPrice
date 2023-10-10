package com.servicetool.api.tradermake;

import com.servicetool.model.H1Candle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TraderMakerResponse {
    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
    public String base_currency;
    public String quote_currency;
    public ArrayList<H1Candle> quotes;

    public void updateTimestamp() {
        if (quotes == null || quotes.isEmpty()) return;
        for (H1Candle h1Candle: quotes) {
            h1Candle.currency = base_currency+quote_currency;
            try {
                Date date = sdf.parse(h1Candle.date);
                h1Candle.setDate(sdf1.format(date));
                h1Candle.setTimestamp(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
