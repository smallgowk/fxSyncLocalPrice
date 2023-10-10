package com.servicetool.db;

import com.servicetool.control.ProcessInputLister;
import com.servicetool.model.ExchangePair;
import com.servicetool.model.Subscription;
import com.servicetool.tcpserver.http.income.BaseIncomeRequestObject;
import com.servicetool.utils.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBAuthenUtil {

    public static ArrayList<Subscription> listSubscriptions;

    private static final String QUERY_GET_LIST_DEFAULT_EX_PAIR =
            "SELECT ep.symbol, ep.src_symbol, ep.des_symbol " +
                    " FROM forexdd_db.exchange_pair ep" +
                    " WHERE ep.default_display = 1";

    private static final String QUERY_GET_LIST_SUBSCRIPTIONS =
            "SELECT s.name, s.description, s.period, s.price " +
                    " FROM forexdd_db.subscription s" +
                    " WHERE s.active = 1" +
                    " ORDER BY s.period";

    public static void init() {
        JdbcUtil.getQuery(QUERY_GET_LIST_DEFAULT_EX_PAIR, new JdbcInterface() {
            @Override
            public void setParams(PreparedStatement ps) {

            }

            @Override
            public void onResult(ResultSet rs) {
                System.out.println("Get defaultExPair success!");
                ExchangePairStore.initDefault(ExchangePair.fromResultSet(rs));
            }

            @Override
            public void onError(SQLException ex) {
                System.out.println("Exception " + ex.toString());
            }
        });

        JdbcUtil.getQuery(QUERY_GET_LIST_SUBSCRIPTIONS, new JdbcInterface() {
            @Override
            public void setParams(PreparedStatement ps) {

            }

            @Override
            public void onResult(ResultSet rs) {
                System.out.println("Get subscription success!");
                listSubscriptions = Subscription.fromResultSet(rs);
            }

            @Override
            public void onError(SQLException ex) {
                System.out.println("Exception " + ex.toString());
            }
        });
    }

    public static void processSendRegistrationKey(BaseIncomeRequestObject bio, ProcessInputLister processInputLister) {
        JdbcUtil.queryTransaction(new JdbcTransactionsInterface() {
            @Override
            public void process(Connection connection, PreparedStatement ps, ResultSet rs) throws SQLException {
                ps = connection.prepareCall("select fcm.token  "
                        + "FROM forexdd_db.fcm_token fcm "
                        + "where fcm.token = ?");
                ps.setString(1, bio.fcmToken);
                rs = ps.executeQuery();

                if (rs.next()) {
                    processInputLister.responseToClient(Constants.ResultCode.SUCCESS);
                } else {
                    int fcmTokenId = 0;
                    ps = connection.prepareCall("insert into forexdd_db.fcm_token (token,create_date, active, agent) values(?, current_timestamp(), 1, ?)");
                    ps.setString(1, bio.getFcmToken());
                    ps.setString(2, bio.getAgent());
                    ps.execute();

                    rs = ps.getGeneratedKeys();
                    if (rs.next()){
                        fcmTokenId = rs.getInt(1);
                    }

                    int userId = 0;
                    ps = connection.prepareCall("insert into forexdd_db.user (create_date, active) " +
                            "values (current_timestamp(), 1)");
                    ps.execute();
                    rs = ps.getGeneratedKeys();
                    if (rs.next()){
                        userId = rs.getInt(1);
                    }

                    ps = connection.prepareCall("insert into forexdd_db.user_fcm_token (user_id,fcm_token_id,create_date, active) values(?, ?,current_timestamp(), 1)");
                    ps.setInt(1, userId);
                    ps.setInt(2, fcmTokenId);
                    ps.execute();

                    ps = connection.prepareCall("insert into forexdd_db.user_subscription (user_id,subscription,create_date, active) values(?, ?,current_timestamp(), 1)");
                    ps.setInt(1, userId);
                    ps.setString(2, getFreeSubscription());
                    ps.execute();

                    for(ExchangePair exchangePair : ExchangePairStore.listExchangePairs) {
                        ps = connection.prepareCall("insert into forexdd_db.user_ex_pair (user_id,exchange_pair,create_date, active) values(?, ?,current_timestamp(), 1)");
                        ps.setInt(1, userId);
                        ps.setString(2, exchangePair.symbol);
                        ps.execute();
                    }
                    processInputLister.responseToClient(Constants.ResultCode.SUCCESS);
                }
            }

            @Override
            public void onError(SQLException ex) {
                System.out.println("Exception with " + ex.toString());
                processInputLister.responseToClient(Constants.ResultCode.FAILURE);
            }
        });
    }

    public static void processUpdateUserInfo(BaseIncomeRequestObject bio, ProcessInputLister processInputLister) {
        JdbcUtil.queryTransaction(new JdbcTransactionsInterface() {
            @Override
            public void process(Connection connection, PreparedStatement ps, ResultSet rs) throws SQLException {
                ps = connection.prepareCall("select uft.user_id"
                        + " FROM forexdd_db.user_fcm_token uft"
                        + " inner join forexdd_db.fcm_token ft on ft.active = 1 and uft.active = 1 and ft.id=uft.fcm_token_id"
                        + " where ft.token = ?");
                ps.setString(1, bio.fcmToken);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    ps = connection.prepareCall("update forexdd_db.user " +
                            "set username=?, email=?, phonenumber=?,update_date=current_timestamp() " +
                            "where id=?");
                    ps.setString(1, bio.username);
                    ps.setString(2, bio.email);
                    ps.setString(3, bio.phoneNumber);
                    ps.setInt(4, userId);
                    ps.execute();
                    processInputLister.responseToClient(Constants.ResultCode.SUCCESS);
                } else {
                    processInputLister.responseToClient(Constants.ResultCode.FAILURE);
                }
            }

            @Override
            public void onError(SQLException ex) {
                System.out.println("Exception " + ex.toString());
                processInputLister.responseToClient(Constants.ResultCode.FAILURE);
            }
        });
    }

    public static void processUpdateUserSubscription(BaseIncomeRequestObject bio, ProcessInputLister processInputLister) {
        JdbcUtil.queryTransaction(new JdbcTransactionsInterface() {
            @Override
            public void process(Connection connection, PreparedStatement ps, ResultSet rs) throws SQLException {
                ps = connection.prepareCall("select uft.user_id"
                        + " FROM forexdd_db.user_fcm_token uft"
                        + " inner join forexdd_db.fcm_token ft on ft.active = 1 and uft.active = 1 and ft.id=uft.fcm_token_id"
                        + " where ft.token = ?");
                ps.setString(1, bio.fcmToken);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    ps = connection.prepareCall("update forexdd_db.user_subscription " +
                            "set subscription=? " +
                            "where user_id=?");
                    ps.setString(1, bio.subscription);
                    ps.setInt(2, userId);
                    ps.execute();
                    processInputLister.responseToClient(Constants.ResultCode.SUCCESS);
                } else {
                    processInputLister.responseToClient(Constants.ResultCode.FAILURE);
                }
            }

            @Override
            public void onError(SQLException ex) {
                System.out.println("Exception " + ex.toString());
                processInputLister.responseToClient(Constants.ResultCode.FAILURE);
            }
        });
    }

    private int getSubPeriod(String sub) {
        switch (sub) {
            case "SUB_1MONTH":
                return 30;
            case "SUB_3MONTH":
                return 90;
            case "SUB_6MONTH":
                return 180;
            default:
                return 0;
        }
    }

    public static String getFreeSubscription() {
        for (Subscription subscription: listSubscriptions) {
            if (subscription.getPrice() == 0) return subscription.name;
        }
        return "FREE";
    }
}
