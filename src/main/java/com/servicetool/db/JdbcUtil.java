package com.servicetool.db;

import com.servicetool.config.SystemInfo;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {
    private static Logger LOGGER = Logger.getLogger("JdbcUtil");

    public static void getQuery(String query, JdbcInterface jdbcInterface) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = ForexDDConnectionPool.getInstance().getConnection();
            ps = connection.prepareCall(query);
            jdbcInterface.setParams(ps);
            rs = ps.executeQuery();
            jdbcInterface.onResult(rs);
        } catch (SQLException ex) {
            jdbcInterface.onError(ex);
            System.out.println("Ex: " + ex);
        } finally {
            System.out.println("123");
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOGGER.debug("Ignored", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LOGGER.debug("Ignored", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.debug("Ignored", e);
                }
            }
        }
    }

    public static void executeQuery(String query, JdbcInterface jdbcInterface) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = ForexDDConnectionPool.getInstance().getConnection();
            ps = connection.prepareCall(query);
            jdbcInterface.setParams(ps);
            ps.execute();
            jdbcInterface.onResult(rs);
        } catch (SQLException ex) {
            jdbcInterface.onError(ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOGGER.debug("Ignored", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LOGGER.debug("Ignored", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.debug("Ignored", e);
                }
            }
        }
    }

    public static void queryTransaction(JdbcTransactionsInterface jdbcInterface) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = ForexDDConnectionPool.getInstance().getConnection();
            jdbcInterface.process(connection, ps, rs);
        } catch (SQLException ex) {
            jdbcInterface.onError(ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOGGER.debug("Ignored", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LOGGER.debug("Ignored", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.debug("Ignored", e);
                }
            }
        }
    }
}
