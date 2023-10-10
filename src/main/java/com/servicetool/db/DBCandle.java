package com.servicetool.db;

import com.servicetool.model.Candle;
import com.servicetool.model.H1Candle;
import com.servicetool.model.MCandle;
import com.servicetool.model.Signal;

import java.sql.*;
import java.util.Calendar;

public class DBCandle {
    public static void updateM15Candle(MCandle candle) {
        JdbcUtil.queryTransaction(new JdbcTransactionsInterface() {
            @Override
            public void process(Connection connection, PreparedStatement ps, ResultSet rs) throws SQLException {
                ps = connection.prepareCall("select c.id  " +
                        "FROM forexdd_db.m15_candle c " +
                        "where c.date = ? and c.currency=? and c.hour=? and c.minute=?");
                ps.setString(1, candle.date);
                ps.setString(2, candle.currency);
                ps.setInt(3, candle.hour);
                ps.setInt(4, candle.minute);
                rs = ps.executeQuery();

                if (rs.next()) { // Existed - Update
                    ps = connection.prepareCall("UPDATE forexdd_db.m15_candle SET close=?, high=?, low=?, bid=?, ask=?, update_time=current_timestamp() " +
                            "WHERE date=? and currency=? and hour=? and minute=?");
                    ps.setString(1, candle.close);
                    ps.setString(2, candle.high);
                    ps.setString(3, candle.low);
                    ps.setString(4, candle.bid);
                    ps.setString(5, candle.ask);
                    ps.setString(6, candle.date);
                    ps.setString(7, candle.currency);
                    ps.setInt(8, candle.hour);
                    ps.setInt(9, candle.minute);
                    ps.execute();
//                    System.out.println("Update!!");
                } else { // Insert
                    ps = connection.prepareCall("INSERT INTO forexdd_db.m15_candle (currency, date, open, close, high, low, bid, ask, hour, minute) " +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    ps.setString(1, candle.currency);
                    ps.setString(2, candle.date);
                    ps.setString(3, candle.open);
                    ps.setString(4, candle.close);
                    ps.setString(5, candle.high);
                    ps.setString(6, candle.low);
                    ps.setString(7, candle.bid);
                    ps.setString(8, candle.ask);
                    ps.setInt(9, candle.hour);
                    ps.setInt(10, candle.minute);
                    ps.execute();
                }
                System.out.println("Success update " + candle);
            }

            @Override
            public void onError(SQLException ex) {
                System.out.println("updateM5Candle " + ex.toString());
            }
        });
    }

    public static void updateM5Candle(MCandle candle) {
        JdbcUtil.queryTransaction(new JdbcTransactionsInterface() {
            @Override
            public void process(Connection connection, PreparedStatement ps, ResultSet rs) throws SQLException {
                ps = connection.prepareCall("select c.id  " +
                        "FROM forexdd_db.m5_candle c " +
                        "where c.date = ? and c.currency=? and c.hour=? and c.minute=?");
                ps.setString(1, candle.date);
                ps.setString(2, candle.currency);
                ps.setInt(3, candle.hour);
                ps.setInt(4, candle.minute);
                rs = ps.executeQuery();

                if (rs.next()) { // Existed - Update
                    ps = connection.prepareCall("UPDATE forexdd_db.m5_candle SET close=?, high=?, low=?, bid=?, ask=?, update_time=current_timestamp() " +
                            "WHERE date=? and currency=? and hour=? and minute=?");
                    ps.setString(1, candle.close);
                    ps.setString(2, candle.high);
                    ps.setString(3, candle.low);
                    ps.setString(4, candle.bid);
                    ps.setString(5, candle.ask);
                    ps.setString(6, candle.date);
                    ps.setString(7, candle.currency);
                    ps.setInt(8, candle.hour);
                    ps.setInt(9, candle.minute);
                    ps.execute();
//                    System.out.println("Update!!");
                } else { // Insert
                    ps = connection.prepareCall("INSERT INTO forexdd_db.m5_candle (currency, date, open, close, high, low, bid, ask, hour, minute, source) " +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    ps.setString(1, candle.currency);
                    ps.setString(2, candle.date);
                    ps.setString(3, candle.open);
                    ps.setString(4, candle.close);
                    ps.setString(5, candle.high);
                    ps.setString(6, candle.low);
                    ps.setString(7, candle.bid);
                    ps.setString(8, candle.ask);
                    ps.setInt(9, candle.hour);
                    ps.setInt(10, candle.minute);
                    ps.setString(11, "Infinox");
                    ps.execute();
                }
                System.out.println("Success update " + candle);
            }

            @Override
            public void onError(SQLException ex) {
                System.out.println("updateM5Candle " + ex.toString());
            }
        });
    }

    public static void updateH1Candle(H1Candle candle) {
        JdbcUtil.queryTransaction(new JdbcTransactionsInterface() {
            @Override
            public void process(Connection connection, PreparedStatement ps, ResultSet rs) throws SQLException {
                ps = connection.prepareCall("select c.id  " +
                        "FROM forexdd_db.h1_candle c " +
                        "where c.date = ? and c.currency=? and c.hour=?");
                ps.setString(1, candle.date);
                ps.setString(2, candle.currency);
                ps.setInt(3, candle.hour);
                rs = ps.executeQuery();

                if (rs.next()) { // Existed - Update
                    ps = connection.prepareCall("UPDATE forexdd_db.h1_candle SET close=?, high=?, low=?, bid=?, ask=?, update_time=current_timestamp() " +
                            "WHERE date=? and currency=? and hour=?");
                    ps.setString(1, candle.close);
                    ps.setString(2, candle.high);
                    ps.setString(3, candle.low);
                    ps.setString(4, candle.bid);
                    ps.setString(5, candle.ask);
                    ps.setString(6, candle.date);
                    ps.setString(7, candle.currency);
                    ps.setInt(8, candle.hour);
                    ps.execute();
//                    System.out.println("Update!!");
                } else { // Insert
                    ps = connection.prepareCall("INSERT INTO forexdd_db.h1_candle (currency, date, open, close, high, low, bid, ask, hour, minute, source) " +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    ps.setString(1, candle.currency);
                    ps.setString(2, candle.date);
                    ps.setString(3, candle.open);
                    ps.setString(4, candle.close);
                    ps.setString(5, candle.high);
                    ps.setString(6, candle.low);
                    ps.setString(7, candle.bid);
                    ps.setString(8, candle.ask);
                    ps.setInt(9, candle.hour);
                    ps.setInt(10, 0);
                    ps.setString(11, "Infinox");
                    ps.execute();
                }
                System.out.println("Success update " + candle);
            }

            @Override
            public void onError(SQLException ex) {
                System.out.println("updateH1Candle " + ex.toString());
            }
        });
    }

    public static void insertSignalInfo(Signal signal) {
        JdbcUtil.executeQuery("INSERT INTO forexdd_db.signal_info " +
                "(currency, date, signal_type, third_h4_open, third_h4_close, first_h1_open, first_h1_close, second_h1_close, " +
                "entry, stop_loss, take_profit) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new JdbcInterface() {
            @Override
            public void setParams(PreparedStatement ps) throws SQLException {
                ps.setString(1, signal.currency);
                ps.setDate(2, new Date(signal.timeStamp));
                ps.setString(3, signal.signalType);
                ps.setFloat(4, signal.thirdH4Open);
                ps.setFloat(5, signal.thirdH4Close);
                ps.setFloat(6, signal.firstH1Open);
                ps.setFloat(7, signal.firstH1Close);
                ps.setFloat(8, signal.secondH1Close);
                ps.setFloat(9, signal.entry);
                ps.setFloat(10, signal.stopLoss);
                ps.setFloat(11, signal.takeProfit);
            }

            @Override
            public void onResult(ResultSet rs) {
//                System.out.println("Sucess!");
            }

            @Override
            public void onError(SQLException ex) {
//                System.out.println("Exception " + ex.toString());
            }
        });
    }

    public static void updateCandle(Candle candle) {

    }

    public static void getListTodayH1Candle(GetModelInterface getModelInterface) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTimeInMillis());
        JdbcUtil.getQuery(
                "SELECT open,close,low,high,bid,ask,currency,hour " +
                        "FROM forexdd_db.h1_candle " +
                        "where date = ? and hour < ? " +
                        "order by currency, hour",
                new JdbcInterface() {
                    @Override
                    public void setParams(PreparedStatement ps) throws SQLException {
                        ps.setDate(1, date);
                        ps.setInt(2, calendar.get(Calendar.HOUR_OF_DAY));
                    }

                    @Override
                    public void onResult(ResultSet rs) {
                        try {
                            while (rs.next()) {
                                RateStore.putH1Candle(H1Candle.fromResultSet(rs, date));
                            }
                        } catch (SQLException throwables) {

                        }
                        getModelInterface.onResult(rs);
                    }

                    @Override
                    public void onError(SQLException ex) {
                        System.out.println("Exception " + ex.toString());
                    }
                }
        );
    }

    public static void getListRangeTodayH1Candle(int fromHour, int toHour, GetModelInterface getModelInterface) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTimeInMillis());
        JdbcUtil.getQuery(
                "SELECT open,close,low,high,bid,ask,currency,hour " +
                        "FROM forexdd_db.h1_candle " +
                        "where date = ? and hour between ? and ?" +
                        "order by currency, hour",
                new JdbcInterface() {
                    @Override
                    public void setParams(PreparedStatement ps) throws SQLException {
                        ps.setDate(1, date);
                        ps.setInt(2, fromHour);
                        ps.setInt(3, toHour);
                    }

                    @Override
                    public void onResult(ResultSet rs) {
                        try {
                            while (rs.next()) {
                                RateStore.putH1Candle(H1Candle.fromResultSet(rs, date));
                            }
                        } catch (SQLException throwables) {

                        }
                        getModelInterface.onResult(rs);
                    }

                    @Override
                    public void onError(SQLException ex) {
                        System.out.println("Exception " + ex.toString());
                    }
                }
        );
    }
}

