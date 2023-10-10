/*
 * The Viettel License
 *
 * Copyright 2012 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.servicetool.db;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

/**
 * Database connection pool. Its services are configuration, managing database
 * connection pool.
 *
 * Suite for using on all VtrackingServer instance internal business and
 * configuration loading.
 *
 * @author Nguyen The Nam <namnt24@viettel.com.vn>
 * @version $Revision: 2813 $
 */
public class ForexDDConnectionPool {

    // Init Log4J
    private static final Logger LOGGER = Logger.getLogger(ForexDDConnectionPool.class.getSimpleName());
    // Init Instance
    private static final ForexDDConnectionPool instance = new ForexDDConnectionPool();
    private ComboPooledDataSource cpds;
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    /**
     * Singleton instance for MTrackingConfig. Remember to call one (and
     * only one) initC3P0 method before using its getConnection() method
     *
     * @return Singleton instance for MTrackingConfig
     */
    public static ForexDDConnectionPool getInstance() {
        return instance;
    }

    /**
     * Prevent outsider from calling construction method.
     */
    private ForexDDConnectionPool() {
    }
    
    public void initC3P0() throws PropertyVetoException {

        if (initialized.getAndSet(false)) {
            LOGGER.warn("Received initC3P0 request, but C3P0 are already initialized");
            return;
        }

        cpds = new ComboPooledDataSource();

        try {
//            BeanUtils.setProperty(cpds, "driverClass", "com.mysql.jdbc.Driver");
            BeanUtils.setProperty(cpds, "driverClass", "com.mysql.cj.jdbc.Driver");
//            BeanUtils.setProperty(cpds, "jdbcUrl", "jdbc:mysql://localhost:3306/forexdd_db?autoReconnect=true");
//            BeanUtils.setProperty(cpds, "jdbcUrl", "jdbc:mysql://207.148.123.8:3306/forexdd_db?autoReconnect=true");
            BeanUtils.setProperty(cpds, "jdbcUrl", "jdbc:mysql://localhost:3306/forexdd_db?autoReconnect=true");
            BeanUtils.setProperty(cpds, "user", "root");
            BeanUtils.setProperty(cpds, "password", "123456");
//            BeanUtils.setProperty(cpds, "password", "forexddaA@");
//            BeanUtils.setProperty(cpds, "password", "forexdd");
            BeanUtils.setProperty(cpds, "minPoolSize", 1);
            BeanUtils.setProperty(cpds, "acquireIncrement", 5);
            BeanUtils.setProperty(cpds, "maxPoolSize", 20);
            
//            LOGGER.info("C3P0 loaded...");

            // Check database driver            
            Class.forName(cpds.getDriverClass());
            
            System.out.println("Success!");

        } catch (ClassNotFoundException e) {
            LOGGER.error("No JDBC driver found, please check the C3P0 configuration part.", e);
        } catch (IllegalAccessException ex) {
            LOGGER.error("IllegalAccessException", ex);
        } catch (InvocationTargetException ex) {
            LOGGER.error("InvocationTargetException", ex);
        }
    }

    /**
     * Get pooled database connection. After using it, just close as normally
     * use.
     *
     * @return Database connection
     * @throws SQLException if error occurred
     */
    public Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }
}
