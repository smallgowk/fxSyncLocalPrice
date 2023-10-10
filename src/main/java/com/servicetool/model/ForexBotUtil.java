package com.servicetool.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ForexBotUtil {

    public static final int H4_TIME_FRAME = 5000;
    public static final int H1_TIME_FRAME = 4000;

    public static final float TP_PIP = 0.002f;

    public static void sortByTimeStamp(ArrayList<Candle> listCandles) {
        if (listCandles == null) return;
        Collections.sort(listCandles, new Comparator<Candle>() {
            @Override
            public int compare(Candle o1, Candle o2) {
                if (o1.timestamp > o2.timestamp) return 1;
                if (o1.timestamp < o2.timestamp) return -1;
                return 0;
            }
        });
    }

    public static DailyCandle buildDailyCandle(ArrayList<Candle> listCandles, long startTime) {
        DailyCandle dailyCandle = new DailyCandle();
        dailyCandle.addH4Candles(buildListH4Candle(listCandles, startTime));
        return dailyCandle;
    }

    public static ArrayList<H4Candle> buildListH4Candle(ArrayList<Candle> listCandles, long startTime) {
        if (listCandles == null) return null;
        ArrayList<H4Candle> listH4Candle = new ArrayList<>();
        ArrayList<H1Candle> listH1Candle = new ArrayList<>();
        H1Candle h1Candle = null;
        long startH1TimeFrame = 0;
        long startH4TimeFrame = 0;
        for(Candle candle : listCandles) {
            if (candle.timestamp < startTime) continue;
            if (startH1TimeFrame == 0) {
                startH1TimeFrame = candle.timestamp;
                h1Candle = new H1Candle();
                h1Candle.addCandle(candle);
                listH1Candle.add(h1Candle);
            }
            if (startH4TimeFrame == 0) {
                startH4TimeFrame = candle.timestamp;
            }
            if (candle.timestamp - startH1TimeFrame < H1_TIME_FRAME) {
                h1Candle.addCandle(candle);
                continue;
            }
            h1Candle = new H1Candle();
            h1Candle.addCandle(candle);
            listH1Candle.add(h1Candle);
            startH1TimeFrame = candle.timestamp;
            if (candle.timestamp - startH4TimeFrame < H4_TIME_FRAME) {
                continue;
            }
            H4Candle h4Candle = new H4Candle();
            h4Candle.copy(candle);
            h4Candle.addH1Candles(listH1Candle);
            listH1Candle.clear();
            listH4Candle.add(h4Candle);
            startH4TimeFrame = candle.timestamp;
        }
        return listH4Candle;
    }

    public static ArrayList<Candle> buildListCandleByTimeFrame(ArrayList<Candle> listCandles, long timeFrame, long startTime, long endTime) {
        if (listCandles == null) return null;
        ArrayList<Candle> result = new ArrayList<>();
        long startTimeFrame = 0;

        for(Candle candle : listCandles) {
            if (candle.timestamp < startTime) continue;
            if (startTimeFrame == 0) {
                startTimeFrame = candle.timestamp;
            }
            if (candle.timestamp - startTimeFrame < timeFrame) continue;
            result.add(candle);
            startTimeFrame = candle.timestamp;
        }
        return result;
    }

    /**
     * B1: Xác định nến ngày
     * B2: Xác định nến H4 thứ 3 của ngày
     * B3: Check nến H1 đầu tiên của cây H4 ở B2
     *  - Nếu đóng nến H1 đầu tiên ( cây H4 thứ 3)  tăng, cây H1 tiếp theo giảm thấp hơn giá mở cửa của H4 thì SELL
     *      + Entry:
     *      + Stop Loss: giá cao nhất của ngày.
     *      + TP1
     *  - Nếu đóng nến H1 đầu tiên giảm, Cây H1 tiếp theo tăng trên giá mở cửa của H4 thì BUY
     *      + Entry:
     *      + Stop Loss: giá thấp nhất của ngày.
     *      + TP1:
     * @param dailyCandle
     * @return
     */
//    public static Signal checkSignal(DailyCandle dailyCandle) {
//        Signal signal = new Signal();
//        if (dailyCandle.listH4Candles.size() >= 3) {
//            // Xác định nến H4 thứ 3
//            H4Candle h4Candle = dailyCandle.listH4Candles.get(2);
//            if (h4Candle.listH1Candles.size() >= 3) {
//                H1Candle firstH1Candle = h4Candle.listH1Candles.get(0);
//                H1Candle secondH1Candle = h4Candle.listH1Candles.get(1);
//                H1Candle thirdH1Candle = h4Candle.listH1Candles.get(2);
//                if (firstH1Candle.isUpCandle()) {
//                    if (secondH1Candle.close < h4Candle.open) {
//                        signal.signalType = Signal.SIGNAL_SELL;
//                        signal.entry = thirdH1Candle.open;
//                        signal.stopLoss = dailyCandle.high;
//                        signal.takeProfit = signal.entry - TP_PIP;
//                    } else {
//                        System.out.println("Chua thoa man tin hieu SELL do gia dong h1 thu 2 >= gia mo cua cay H4 thu 3");
//                    }
//                } else if (firstH1Candle.isDownCandle()) {
//                    if (secondH1Candle.close > h4Candle.open) {
//                        signal.signalType = Signal.SIGNAL_BUY;
//                        signal.entry = thirdH1Candle.open;
//                        signal.stopLoss = dailyCandle.low;
//                        signal.takeProfit = signal.entry + TP_PIP;
//                    } else {
//                        System.out.println("Chua thoa man tin hieu BUY do gia dong h1 thu 2 <= gia mo cua cay H4 thu 3");
//                    }
//                } else {
//                    System.out.println("Nen doji");
//                }
//            } else {
//                System.out.println("Chua du nen H1");
//            }
//        } else {
//            System.out.println("Chua du nen h4");
//        }
//        return signal;
//    }
}
