package com.servicetool.db;

import com.servicetool.model.fcm.FcmToken;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBFcmUtil {

    private static final String QUERY_GET_LIST_ALL_AVAI_FCM_TOKEN =
            "SELECT f.token " +
                    " FROM forexdd_db.fcm_token f" +
                    " inner join forexdd_db.user_fcm_token uft on uft.active = 1 and uft.fcm_token_id = f.id" +
                    " inner join forexdd_db.user_subscription us on us.active = 1 and us.user_id = uft.user_id" +
                    " inner join forexdd_db.subscription s " +
                    "    on s.active = 1 and s.name = us.subscription and DATEDIFF(curdate(),us.create_date) <= s.period" +
                    " where f.active = 1";

    private static final String QUERY_GET_LIST_ALL_FCM_TOKEN_BY_SUB_AGENT =
            "SELECT f.token " +
                    " FROM forexdd_db.fcm_token f" +
                    " inner join forexdd_db.user_fcm_token uft on uft.active = 1 and uft.fcm_token_id = f.id" +
                    " inner join forexdd_db.user_subscription us on us.active = 1 and us.user_id = uft.user_id" +
                    " inner join forexdd_db.subscription s " +
                    "    on s.active = 1 and s.name = us.subscription" +
                    " where f.active = 1 and s.name=? and f.agent=?";

    private static final String QUERY_GET_LIST_AVAI_FCM_TOKEN_BY_SUB_AGENT =
            "SELECT f.token " +
                    " FROM forexdd_db.fcm_token f" +
                    " inner join forexdd_db.user_fcm_token uft on uft.active = 1 and uft.fcm_token_id = f.id" +
                    " inner join forexdd_db.user_subscription us on us.active = 1 and us.user_id = uft.user_id" +
                    " inner join forexdd_db.subscription s " +
                    "    on s.active = 1 and s.name = us.subscription and DATEDIFF(curdate(),us.create_date) <= s.period" +
                    " where f.active = 1 and s.name=? and f.agent=?";

    private static final String QUERY_GET_LIST_ALL_FCM_TOKEN_BY_AGENT =
            "SELECT f.token " +
                    " FROM forexdd_db.fcm_token f" +
                    " inner join forexdd_db.user_fcm_token uft on uft.active = 1 and uft.fcm_token_id = f.id" +
                    " inner join forexdd_db.user_subscription us on us.active = 1 and us.user_id = uft.user_id" +
                    " inner join forexdd_db.subscription s " +
                    "    on s.active = 1 and s.name = us.subscription" +
                    " where f.active = 1 and f.agent=?";

    private static final String QUERY_GET_LIST_AVAI_FCM_TOKEN_BY_AGENT =
            "SELECT f.token " +
                    " FROM forexdd_db.fcm_token f" +
                    " inner join forexdd_db.user_fcm_token uft on uft.active = 1 and uft.fcm_token_id = f.id" +
                    " inner join forexdd_db.user_subscription us on us.active = 1 and us.user_id = uft.user_id" +
                    " inner join forexdd_db.subscription s " +
                    "    on s.active = 1 and s.name = us.subscription and DATEDIFF(curdate(),us.create_date) <= s.period" +
                    " where f.active = 1 and f.agent=?";


    public static void getListAvaiFcmTokensByPairAgent(String agent, String pair, GetModelInterface getModelInterface) {
        JdbcUtil.getQuery(
                "SELECT f.token " +
                        " FROM forexdd_db.fcm_token f" +
                        " inner join forexdd_db.user_fcm_token uft on uft.active = 1 and uft.fcm_token_id = f.id" +
                        " inner join forexdd_db.user_subscription us on us.active = 1 and us.user_id = uft.user_id" +
                        " inner join forexdd_db.subscription s " +
                        "    on s.active = 1 and s.name = us.subscription and DATEDIFF(curdate(),us.create_date) <= s.period" +
                        " inner join forexdd_db.user_ex_pair uep on uep.active = 1 and uep.user_id = uft.user_id" +
                        " where f.active = 1 and f.agent=? and uep.exchange_pair=?",
                new JdbcInterface() {
                    @Override
                    public void setParams(PreparedStatement ps) throws SQLException {
                        ps.setString(1, agent);
                        ps.setString(2, pair);
                    }

                    @Override
                    public void onResult(ResultSet rs) {
                        getModelInterface.onResult(FcmToken.listTokenFromResultSet(rs));
                    }

                    @Override
                    public void onError(SQLException ex) {
                        System.out.println("Exception " + ex.toString());
                    }
                }
        );
    }

    public static void getListAvaiFcmTokens(GetModelInterface getModelInterface) {
        JdbcUtil.getQuery(QUERY_GET_LIST_ALL_AVAI_FCM_TOKEN, new JdbcInterface() {
            @Override
            public void setParams(PreparedStatement ps) {

            }

            @Override
            public void onResult(ResultSet rs) {
                getModelInterface.onResult(FcmToken.listTokenFromResultSet(rs));
            }

            @Override
            public void onError(SQLException ex) {
                System.out.println("Exception " + ex.toString());
            }
        });
    }

    public static void getListAvaiFcmTokensByAgent(String agent, GetModelInterface getModelInterface) {
        JdbcUtil.getQuery(QUERY_GET_LIST_AVAI_FCM_TOKEN_BY_AGENT, new JdbcInterface() {
            @Override
            public void setParams(PreparedStatement ps) throws SQLException {
                ps.setString(1, agent);
            }

            @Override
            public void onResult(ResultSet rs) {
                getModelInterface.onResult(FcmToken.listTokenFromResultSet(rs));
            }

            @Override
            public void onError(SQLException ex) {
                System.out.println("Exception " + ex.toString());
            }
        });
    }

    public static void getListAllFcmTokensByAgent(String agent, GetModelInterface getModelInterface) {
        JdbcUtil.getQuery(QUERY_GET_LIST_ALL_FCM_TOKEN_BY_AGENT, new JdbcInterface() {
            @Override
            public void setParams(PreparedStatement ps) throws SQLException {
                ps.setString(1, agent);
            }

            @Override
            public void onResult(ResultSet rs) {
                getModelInterface.onResult(FcmToken.listTokenFromResultSet(rs));
            }

            @Override
            public void onError(SQLException ex) {
                System.out.println("Exception " + ex.toString());
            }
        });
    }

    public static void getListTokensBySubscriptionAndAgent(String subscription, boolean isAvailable, String agent, GetModelInterface getModelInterface) {
        String query = isAvailable ? QUERY_GET_LIST_AVAI_FCM_TOKEN_BY_SUB_AGENT : QUERY_GET_LIST_ALL_FCM_TOKEN_BY_SUB_AGENT;
        JdbcUtil.getQuery(query, new JdbcInterface() {
            @Override
            public void setParams(PreparedStatement ps) throws SQLException {
                ps.setString(1, subscription);
                ps.setString(2, agent);
            }

            @Override
            public void onResult(ResultSet rs) {
                getModelInterface.onResult(FcmToken.listTokenFromResultSet(rs));
            }

            @Override
            public void onError(SQLException ex) {
                System.out.println("Exception " + ex.toString());
            }
        });
    }
}
