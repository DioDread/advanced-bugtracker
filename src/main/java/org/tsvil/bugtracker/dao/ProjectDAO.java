package org.tsvil.bugtracker.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import org.tsvil.bugtracker.AppConfig;
import org.tsvil.bugtracker.entity.Project;

public class ProjectDAO {

    private Connection connection;
    private Statement statement;
    private final DBConnect dbc;

    public ProjectDAO() {
        dbc = new DBConnect();
    }

    public ArrayList<Project> getAllProjects() throws SQLException {
        ArrayList<Project> projects = new ArrayList<>();
        try {
            String query = "select project_id, project from `" + AppConfig.getDbName() + "`.project;";
            connection = dbc.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                projects.add(new Project(rs.getInt("project_id"), rs.getString("project")));
            }
        } catch (SQLException | NamingException | IOException ex) {
            Logger.getLogger(BugReportDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources();
        }
        return projects;
    }

    public Project getProjectById(long id) throws SQLException {
        Project project = null;
        try {
            String query = "select project from `" + AppConfig.getDbName() + "`.project where project_id=" + id;
            connection = dbc.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                project = new Project(id, rs.getString("project"));
            }
        } catch (SQLException | NamingException | IOException ex) {
            Logger.getLogger(BugReportDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources();
        }
        return project;
    }

    private void releaseResources() throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
