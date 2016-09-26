package org.tsvil.bugtracker.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import org.tsvil.bugtracker.AppConfig;
import org.tsvil.bugtracker.entity.Label;

public class LabelDAO {

    private final DBConnect dbc;
    private Connection connection;

    public LabelDAO() {
        dbc = new DBConnect();
    }

    public Label getLabelById(long id) {
        Statement stmt;
        Label label = null;
        try {
            String query = "select name, color from `" + AppConfig.getDbName() + "`.label where label_id=" + id;
            connection = dbc.getConnection();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                label = new Label(id, rs.getString("name"), rs.getInt("color"));
            }
        } catch (NamingException | SQLException | IOException ex) {
            Logger.getLogger(LabelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return label;
    }

}
