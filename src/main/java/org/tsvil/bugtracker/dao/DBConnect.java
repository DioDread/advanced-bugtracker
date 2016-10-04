package org.tsvil.bugtracker.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.tsvil.bugtracker.AppConfig;

public class DBConnect {

    private DataSource source;
    private Context initialContext;

    public Connection getConnection() throws SQLException, ConfigurationException {
        try {
            initialContext = new InitialContext();
            source = (DataSource) initialContext.lookup(AppConfig.getDbResourceName());
        } catch (NamingException | IOException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConfigurationException("Application configuration failed, please contact your administrator");
        }
        return source.getConnection();
    }
}
