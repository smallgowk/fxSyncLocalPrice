package com.servicetool.db;

import java.sql.SQLException;

public interface ExecuteDBInterface {
    public void onResult(Object object);
    public void onError(SQLException ex);
}
