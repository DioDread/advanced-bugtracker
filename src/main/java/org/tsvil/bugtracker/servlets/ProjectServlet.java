package org.tsvil.bugtracker.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tsvil.bugtracker.dao.ProjectDAO;
import org.tsvil.bugtracker.entity.Project;

public class ProjectServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProjectDAO projectDAO = new ProjectDAO();
        try {
            ArrayList<Project> projects = projectDAO.getAllProjects();
            String projectsJsonString = "[";
            resp.setContentType("applicaton/json");
            PrintWriter out = resp.getWriter();
            for (Project project : projects) {
                projectsJsonString += "{\"projectId\":\"" + project.getProjectId() + "\", \"name\":\"" + project.getName() 
                        + "\"}" + ((int)project.getProjectId() == (projects.size() - 1) ? "" : ",");
            }
            projectsJsonString += "]";
            out.append(projectsJsonString);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
