package org.tsvil.bugtracker.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

public class BugReportDAO implements DBWriter {

    private Connection connection;
    private DBConnect dbc;

    public BugReportDAO() {
        try {
            dbc = new DBConnect();
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(BugReportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void doTest() throws SQLException {

        connection = dbc.getConnection();

        Integer i = 1;
    }

}
