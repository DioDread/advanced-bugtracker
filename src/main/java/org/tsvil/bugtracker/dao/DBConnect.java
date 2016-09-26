package org.tsvil.bugtracker.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.tsvil.bugtracker.AppConfig;

public class DBConnect {

    private DataSource source;
    private Context initialContext;

    public Connection getConnection() throws NamingException, SQLException, IOException {
        initialContext = new InitialContext();
        source = (DataSource) initialContext.lookup(AppConfig.getDbResourceName());
        return source.getConnection();
    }
}
