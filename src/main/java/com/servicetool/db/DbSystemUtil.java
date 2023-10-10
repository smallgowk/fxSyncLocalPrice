package com.servicetool.db;

import com.servicetool.model.Market;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class DbSystemUtil {

    private static final String QUERY_GET_LIST_MARKET = "SELECT * FROM forexdd_db.market";

    public static ArrayList<Market> listMarkets;
    public static Market market;

    public static void init() {
//        JdbcUtil.query(QUERY_GET_LIST_MARKET, new JdbcInterface() {
//            @Override
//            public void setParams(PreparedStatement ps) {
//
//            }
//
//            @Override
//            public void onResult(ResultSet rs) {
//                listMarkets = Market.fromResultSet(rs);
//                market = getCurrentMarketBySeason("USA", "SUMMER");
//            }
//
//            @Override
//            public void onError(SQLException ex) {
//                System.out.println("Exception " + ex.toString());
//            }
//        });
    }

    public static Market getCurrentMarketBySeason(String area, String season) {
        if (listMarkets == null || listMarkets.isEmpty()) return null;

        for (Market m : listMarkets) {
            if (season.equals(m.getSeason()) && area.equals(m.getName())) {
                return m;
            }
        }
        return null;
    }

    public static String getCurrentSeason() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        if (month >= 3 && month <= 9) {
            return "SUMMER";
        } else {
            return "WINTER";
        }
    }

    public static String getCurrentArea() {
        return "USA";
    }
}
