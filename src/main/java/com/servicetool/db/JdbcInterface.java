package com.servicetool.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface JdbcInterface {
    public void setParams(PreparedStatement ps) throws SQLException;
    public void onResult(ResultSet rs);
    public void onError(SQLException ex);
}
