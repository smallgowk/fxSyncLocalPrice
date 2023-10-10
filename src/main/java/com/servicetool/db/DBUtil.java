/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.db;

import com.servicetool.authen.Account;
import com.servicetool.authen.ComputerLicense;
import com.servicetool.config.SystemInfo;
import com.servicetool.utils.StringUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author PhanDuy
 */
public class DBUtil {

    private static Logger LOGGER = Logger.getLogger("DBUtil");

    public static SystemInfo getSystemInfo() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = ForexDDConnectionPool.getInstance().getConnection();
            ps = connection.prepareCall("SELECT lastest_version,force_version FROM dropship_db.system_info");
            rs = ps.executeQuery();

            if (rs.next()) {
                SystemInfo systemInfo = new SystemInfo();
                systemInfo.setLastestVersion(rs.getString("lastest_version"));
                systemInfo.setForceVersion(rs.getString("force_version"));
                return systemInfo;
            }

        } catch (SQLException ex) {
            System.out.println("" + ex.toString());
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
        return null;
    }

    public static boolean checkAccountInfoExisted(String username, String email) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = ForexDDConnectionPool.getInstance().getConnection();
            ps = connection.prepareCall("select count(username) as total FROM dropship_db.user where active = 1 and username = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                return total > 0;
            }
        } catch (SQLException ex) {
            System.out.println("" + ex.toString());
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
        return false;
    }

    public static boolean checkComputerAvail(String serial) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = ForexDDConnectionPool.getInstance().getConnection();
            ps = connection.prepareCall("select count(id) as total FROM dropship_db.computer where serial = ?");
            ps.setString(1, serial);
            rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                return total > 0;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("" + ex.toString());
            return false;
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
