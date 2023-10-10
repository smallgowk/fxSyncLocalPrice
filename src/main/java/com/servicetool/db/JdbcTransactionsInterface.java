package com.servicetool.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface JdbcTransactionsInterface {
    public void process(Connection connection, PreparedStatement ps, ResultSet rs) throws SQLException;
    public void onError(SQLException ex);
}
