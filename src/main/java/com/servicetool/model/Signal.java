package com.servicetool.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Signal {

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static final String SIGNAL_NONE = "NONE";
    public static final String SIGNAL_BUY = "BUY";
    public static final String SIGNAL_SELL = "SELL";

    public String signalType;
    public float entry;
    public float stopLoss;
    public float takeProfit;
    public String currency;
    public long timeStamp;
    public float thirdH4Open;
    public float thirdH4Close;
    public float firstH1Open;
    public float firstH1Close;
    public float secondH1Close;
    public String date;

    public String getSignalType() {
        return signalType;
    }

    public void setSignalType(String signalType) {
        this.signalType = signalType;
    }

    public float getEntry() {
        return entry;
    }

    public void setEntry(float entry) {
        this.entry = entry;
    }

    public float getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(float stopLoss) {
        this.stopLoss = stopLoss;
    }

    public float getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(float takeProfit) {
        this.takeProfit = takeProfit;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public float getThirdH4Open() {
        return thirdH4Open;
    }

    public void setThirdH4Open(float thirdH4Open) {
        this.thirdH4Open = thirdH4Open;
    }

    public float getThirdH4Close() {
        return thirdH4Close;
    }

    public void setThirdH4Close(float thirdH4Close) {
        this.thirdH4Close = thirdH4Close;
    }

    public float getFirstH1Open() {
        return firstH1Open;
    }

    public void setFirstH1Open(float firstH1Open) {
        this.firstH1Open = firstH1Open;
    }

    public float getFirstH1Close() {
        return firstH1Close;
    }

    public void setFirstH1Close(float firstH1Close) {
        this.firstH1Close = firstH1Close;
    }

    public float getSecondH1Close() {
        return secondH1Close;
    }

    public void setSecondH1Close(float secondH1Close) {
        this.secondH1Close = secondH1Close;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Signal{" +
                "signalType='" + signalType + '\'' +
                ", entry=" + entry +
                ", stopLoss=" + stopLoss +
                ", takeProfit=" + takeProfit +
                ", currency='" + currency + '\'' +
                ", timeStamp=" + timeStamp +
                ", thirdH4Open=" + thirdH4Open +
                ", thirdH4Close=" + thirdH4Close +
                ", firstH1Open=" + firstH1Open +
                ", firstH1Close=" + firstH1Close +
                ", secondH1Close=" + secondH1Close +
                ", date='" + date + '\'' +
                '}';
    }

    public String getBodyContent() {
        StringBuilder sb = new StringBuilder();
        if (signalType.equals(SIGNAL_NONE)) {
            sb.append("Khong to tin hieu cho ").append(currency);
        } else {
            sb.append(signalType).append(" ").append(currency)
                    .append(" at ").append(entry)
                    .append(", tp: ").append(takeProfit)
                    .append(", sl: ").append(stopLoss)
                    .append(", ts: ").append(sdf.format(new Date(timeStamp)));
        }

        return sb.toString();
    }

    public HashMap<String, String> getFcmDatas() {
        HashMap<String, String> datas = new HashMap<>();
        datas.put("signal", signalType);
        datas.put("entry", "" + entry);
        datas.put("takeProfit", "" + takeProfit);
        datas.put("stopLoss", "" + stopLoss);
        datas.put("currency", currency);
        datas.put("timeStamp", "" + timeStamp);
        return datas;
    }

    public boolean hasSignal() {
        return !signalType.equals(SIGNAL_NONE);
    }
}
