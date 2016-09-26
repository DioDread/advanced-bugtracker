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
import org.tsvil.bugtracker.entity.Project;

public class ProjectDAO {

    private Connection connection;
    private final DBConnect dbc;

    public ProjectDAO() {
        dbc = new DBConnect();
    }

    public Project getProjectById(long id) throws SQLException {
        Project project = null;
        Statement stmt = null;
        try {
            String query = "select project from `" + AppConfig.getDbName() + "`.project where project_id=" + id;
            connection = dbc.getConnection();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                project = new Project(id, rs.getString("project"));
            }
        } catch (SQLException | NamingException | IOException ex) {
            Logger.getLogger(BugReportDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return project;
    }
}
