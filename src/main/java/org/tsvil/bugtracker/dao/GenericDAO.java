package org.tsvil.bugtracker.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericDAO {

    protected Connection connection;
    protected Statement statement;
    protected final DBConnect dbc;

    public GenericDAO() {
        dbc = new DBConnect();
    }
    
    protected void releaseResources() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
