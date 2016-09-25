package org.tsvil.bugtracker.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnect {

    private final DataSource source;
    private final Context initialContext;
    
    private static final String DB_RESOURCE_NAME = "java:/comp/env/jdbc/advanced-bugtracker";

    public DBConnect() throws NamingException, SQLException {
        initialContext = new InitialContext();
        source = (DataSource) initialContext.lookup(DB_RESOURCE_NAME);
    }
    
    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }
}
