package com.servicetool.control;

import com.google.gson.Gson;
import com.servicetool.db.ForexDDConnectionPool;
import com.servicetool.model.fcm.FcmToken;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProcessFcmSvs {
    private static Logger LOGGER = Logger.getLogger("ProcessFcmSvs");
    private static Gson gson = new Gson();

    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy'_'HH:mm:ss");
    public static final SimpleDateFormat sdfFile = new SimpleDateFormat("yyyyMMdd'_'HHmmss");

    public static ArrayList<FcmToken> getListFcmTokens() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<FcmToken> results = null;

        try {
            connection = ForexDDConnectionPool.getInstance().getConnection();

            ps = connection.prepareCall("SELECT f.token,f.agent " +
                    " FROM forexdd_db.fcm_token f" +
                    " inner join forexdd_db.user_fcm_token uft on uft.active = 1 and uft.fcm_token_id = f.id" +
                    " inner join forexdd_db.user_subscription us on us.active = 1 and us.user_id = uft.user_id" +
                    " inner join forexdd_db.subscription s " +
                    "    on s.active = 1 and s.name = us.subscription and DATEDIFF(curdate(),us.create_date) <= s.period" +
                    " where f.active = 1");
            rs = ps.executeQuery();

            while (rs.next()) {
                if (results == null) {
                    results = new ArrayList<>();
                }
                FcmToken fcmToken = new FcmToken();
                fcmToken.setToken(rs.getString(1));
                fcmToken.setAgent(rs.getString(2));
                results.add(fcmToken);
            }
        } catch (SQLException ex) {
            System.out.println("Exception " + ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return results;
    }

    public static ArrayList<FcmToken> getListFcmTokensBySubscription(String subscription, boolean isAvailable) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<FcmToken> results = null;

        try {
            connection = ForexDDConnectionPool.getInstance().getConnection();

            if (isAvailable) {
                ps = connection.prepareCall("SELECT f.token,f.agent " +
                        " FROM forexdd_db.fcm_token f" +
                        " inner join forexdd_db.user_fcm_token uft on uft.active = 1 and uft.fcm_token_id = f.id" +
                        " inner join forexdd_db.user_subscription us on us.active = 1 and us.user_id = uft.user_id" +
                        " inner join forexdd_db.subscription s " +
                        "    on s.active = 1 and s.name = us.subscription and DATEDIFF(curdate(),us.create_date) <= s.period" +
                        " where f.active = 1 and s.name=?");
                ps.setString(1, subscription);
            } else {
                ps = connection.prepareCall("SELECT f.token,f.agent " +
                        " FROM forexdd_db.fcm_token f" +
                        " inner join forexdd_db.user_fcm_token uft on uft.active = 1 and uft.fcm_token_id = f.id" +
                        " inner join forexdd_db.user_subscription us on us.active = 1 and us.user_id = uft.user_id" +
                        " inner join forexdd_db.subscription s " +
                        "    on s.active = 1 and s.name = us.subscription" +
                        " where f.active = 1 and s.name=?");
                ps.setString(1, subscription);
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                if (results == null) {
                    results = new ArrayList<>();
                }
                FcmToken fcmToken = new FcmToken();
                fcmToken.setToken(rs.getString(1));
                fcmToken.setAgent(rs.getString(2));
                results.add(fcmToken);
            }
        } catch (SQLException ex) {
            System.out.println("Exception " + ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return results;
    }

    public static List<String> getListTokenByAgent(ArrayList<FcmToken> listTokens, String agent) {
        return listTokens.stream()
                .filter(fcmToken -> fcmToken.agent.equals(agent))
                .map(fcmToken -> fcmToken.token)
                .collect(Collectors.toList());
    }

    public static ArrayList<String> getListTokensByAgent(String agent) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> results = null;

        try {
            connection = ForexDDConnectionPool.getInstance().getConnection();

            ps = connection.prepareCall("SELECT f.token,f.agent " +
                    " FROM forexdd_db.fcm_token f" +
                    " inner join forexdd_db.user_fcm_token uft on uft.active = 1 and uft.fcm_token_id = f.id" +
                    " inner join forexdd_db.user_subscription us on us.active = 1 and us.user_id = uft.user_id" +
                    " inner join forexdd_db.subscription s " +
                    "    on s.active = 1 and s.name = us.subscription and DATEDIFF(curdate(),us.create_date) <= s.period" +
                    " where f.active = 1 and f.agent=?");
            ps.setString(1, agent);
            rs = ps.executeQuery();

            while (rs.next()) {
                if (results == null) {
                    results = new ArrayList<>();
                }
                results.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println("Exception " + ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return results;
    }

    public static ArrayList<String> getListTokensBySubscriptionAndAgent(String subscription, boolean isAvailable, String agent) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> results = null;

        try {
            connection = ForexDDConnectionPool.getInstance().getConnection();

            if (isAvailable) {
                ps = connection.prepareCall("SELECT f.token " +
                        " FROM forexdd_db.fcm_token f" +
                        " inner join forexdd_db.user_fcm_token uft on uft.active = 1 and uft.fcm_token_id = f.id" +
                        " inner join forexdd_db.user_subscription us on us.active = 1 and us.user_id = uft.user_id" +
                        " inner join forexdd_db.subscription s " +
                        "    on s.active = 1 and s.name = us.subscription and DATEDIFF(curdate(),us.create_date) <= s.period" +
                        " where f.active = 1 and s.name=? and f.agent=?");
                ps.setString(1, subscription);
                ps.setString(2, agent);
            } else {
                ps = connection.prepareCall("SELECT f.token " +
                        " FROM forexdd_db.fcm_token f" +
                        " inner join forexdd_db.user_fcm_token uft on uft.active = 1 and uft.fcm_token_id = f.id" +
                        " inner join forexdd_db.user_subscription us on us.active = 1 and us.user_id = uft.user_id" +
                        " inner join forexdd_db.subscription s " +
                        "    on s.active = 1 and s.name = us.subscription" +
                        " where f.active = 1 and s.name=? and f.agent=?");
                ps.setString(1, subscription);
                ps.setString(1, agent);
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                if (results == null) {
                    results = new ArrayList<>();
                }
                results.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println("Exception " + ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return results;
    }
}
