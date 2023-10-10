/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.server.process;

import com.servicetool.db.DBCandle;
import com.servicetool.model.H1Candle;
import com.servicetool.model.MCandle;
import com.servicetool.utils.ReadMetaData;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author PhanDuy
 */
public class ProcessPriceThread extends Thread {
    private static Logger LOGGER;

//    static final String FILE_NAME = "GBPUSDH1.txt";
//    static final String FILE_NAME = "XAUUSD5.txt";
//    static final String FILE_NAME = "XAUUSD15.txt";
//    static final String FILE_NAME = "XAUUSDH1.txt";
//    static final String FILE_NAME = "GBPUSDH1.txt";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    boolean isRunning = true;
    public ProcessPriceThread() {
        LOGGER = Logger.getLogger("ProcessUpdatePriceThread");
    }

    public void stopThread() {
        isRunning = false;
        try {
            interrupt();
        } catch (Exception ex) {

        }
    }
    
    @Override
    public void run() {
//        String symbol = "XAUUSD";
//        String[] symbols = new String[] {
//                "GBPUSD",
//                "GBPAUD",
//                "GBPCAD",
//                "GBPJPY",
//                "GBPCHF",
//                "GBPNZD",
//                "EURAUD",
//                "EURUSD"
//        };

//        String[] symbols = new String[] {
//                "XAUUSD",
//                "EURUSD",
//                "GBPUSD",
//                "AUDUSD",
//                "NZDUSD",
//                "USDCAD",
//                "USDCHF",
//                "USDJPY",
//                "GBPJPY",
//                "GBPCHF",
//                "GBPCAD",
//                "GBPAUD",
//                "GBPNZD",
//                "EURGBP",
//                "EURJPY",
//                "EURAUD",
//                "EURCAD",
//                "EURNZD"
//        };

        String[] symbols = new String[] {
                "XAUUSD"
        };

//        String timeFrame = "H1";
//        String timeFrame = "M15";
        String timeFrame = "M5";
        for(String symbol: symbols) {
            switch (timeFrame) {
                case "H1":
                    ArrayList<H1Candle> listH1Candle = ReadMetaData.readH1Data(
                            "D:\\SourceCode\\Forex\\fxSyncHistoryPrice\\etc\\" + symbol+timeFrame+".txt",
                            symbol
                    );
                    for (H1Candle candle: listH1Candle) {
                        DBCandle.updateH1Candle(candle);
                    }
                    break;
                case "M5":
                    ArrayList<MCandle> listM5Candle = ReadMetaData.readMData(
                            "D:\\SourceCode\\Forex\\fxSyncHistoryPrice\\etc\\" + symbol+timeFrame+".txt",
                            symbol
                    );
                    for (MCandle candle: listM5Candle) {
                        DBCandle.updateM5Candle(candle);
                    }
                    break;
            }
        }

//        ArrayList<MCandle> listCandle = ReadMetaData.readMData(
//                "D:\\SourceCode\\Forex\\fxSyncHistoryPrice\\etc\\" + FILE_NAME,
//                "XAUUSD"
//        );
//        for (MCandle candle: listCandle) {
//            DBCandle.updateM15Candle(candle);
//        }
    }
}
